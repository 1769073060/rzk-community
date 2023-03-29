package com.rzk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import com.rzk.mapper.dao.WxAppParsingInfoMapper;
import com.rzk.pojo.WxAppParsingInfo;
import com.rzk.pojo.WxAppUser;
import com.rzk.pojo.dto.VideoInfoDto;
import com.rzk.service.VideoService;
import com.rzk.service.WxAppUserService;
import com.rzk.utils.RestTemplateUtil;
import com.rzk.utils.exception.Asserts;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: 小熊
 * @date: 2021/8/27 14:33
 * @description:phone 17521111022
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Resource(name = "wxAppUserService")
    private WxAppUserService wxAppUserService;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private RestTemplateUtil restTemplateUtil;

    @Resource
    private WxAppParsingInfoMapper parsingInfoMapper;

    @Override
    public VideoInfoDto getVideoInfo(String openid, String url) {
        WxAppUser wxUser = wxAppUserService.getUserInfoByOpenId(openid);
        if (wxUser == null) {
            Asserts.wxInfoFail("未查到用户信息");
        } else if (wxUser.getVideoNumber() < 1) {
            Asserts.wxInfoFail("解析次数已用完");
        }
        isContainsStrings(url);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("User-Agent", "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Mobile Safari/537.36");
        httpHeaders.set("Referer", url);
        VideoInfoDto videoInfoDto;
        //抖音快手Java解析其余短视频平台Java版本暂时没时间写先用php
        if (url.contains("douyin")) {
            videoInfoDto = parsingDyVideoInfo(url, httpHeaders);
        } else if (url.contains("kuaishou")) {
            videoInfoDto = parsingKsuVideoInfo(url, httpHeaders);
        } else {
            videoInfoDto = phpParsingVideoInfo(url);
        }
        wxUser.setVideoNumber(wxUser.getVideoNumber() - 1);
        wxUser.setLastParsingTime(new Date());
        wxAppUserService.updateById(wxUser);
        WxAppParsingInfo parsingInfo = new WxAppParsingInfo();
        parsingInfo.setTitle(videoInfoDto.getTitle());
        parsingInfo.setDownloadUrl(videoInfoDto.getUrl());
        parsingInfo.setAuthor(videoInfoDto.getAuthor());
        parsingInfo.setCover(videoInfoDto.getCover());
        parsingInfo.setUserOpenId(wxUser.getOpenId());
        parsingInfo.setCreateTime(new Date());
        parsingInfoMapper.insert(parsingInfo);
        return videoInfoDto;
    }

    @Override
    public VideoInfoDto parsingDyVideoInfo(String url, HttpHeaders httpHeaders) {
        //获取重定向后的地址
        url = restTemplate.headForHeaders(url).getLocation().toString();
        Matcher matcher = Pattern.compile("/share/video/([\\d]*)[/|?]").matcher(url);
        if (!matcher.find())
            Asserts.urlParsingFail("解析链接ID异常");
        //获取链接ID
        String id = matcher.group(1);
        String dyWebApi = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=" + id;
        String content = restTemplateUtil.getForObject(dyWebApi, httpHeaders, String.class);
        Asserts.urlInfoNotNull(content, "API请求异常");
        JSONObject videoInfo = JSON.parseObject(content).getJSONArray("item_list").getJSONObject(0);
        VideoInfoDto videoInfoDto = new VideoInfoDto();
        videoInfoDto.setTime(videoInfo.getString("create_time"));
        videoInfoDto.setCover(videoInfo.getJSONObject("video").getJSONObject("origin_cover").getJSONArray("url_list").getString(0));
        videoInfoDto.setUrl(videoInfo.getJSONObject("video").getJSONObject("play_addr").getJSONArray("url_list").getString(0).replace("playwm", "play"));
        videoInfoDto.setTitle(videoInfo.getString("desc"));
        videoInfoDto.setAuthor(videoInfo.getJSONObject("author").getString("nickname"));
        videoInfoDto.setAvatar(videoInfo.getJSONObject("author").getJSONObject("avatar_larger").getJSONArray("url_list").getString(0));
        return videoInfoDto;
    }

    @Override
    public VideoInfoDto parsingKsuVideoInfo(String url, HttpHeaders httpHeaders) {
        Matcher matcher = Pattern.compile("(https?://v.kuaishou.com/[\\S]*)").matcher(url);
        VideoInfoDto videoInfoDto = null;
        if (matcher.find()) {
            url = matcher.group(1);
            url = restTemplate.headForHeaders(url).getLocation().toString();
            String htmlContent = restTemplateUtil.getForObject(url, httpHeaders, String.class);
            Document document = Jsoup.parse(htmlContent);
            Elements elements = document.getElementsByTag("script");
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).childNodeSize() > 0) {
                    Matcher matcherForPageData = Pattern.compile("window.pageData[\\s]*=[\\s]*(.*)[\\s]*").matcher(elements.get(i).childNode(0).toString());
                    if (matcherForPageData.find()) {
                        String pageData = matcherForPageData.group(1);
                        JSONObject pageDataOb = JSONObject.parseObject(pageData);
                        videoInfoDto = new VideoInfoDto();
                        JSONObject mediaJob = pageDataOb.getJSONObject("video");
                        String photoType = mediaJob.getString("type");
                        if ("video".equals(photoType)) {
                            videoInfoDto.setUrl(mediaJob.getString("srcNoMark"));
                        } else {
                            Asserts.urlParsingFail("暂时只支持视频解析");
                        }
                        videoInfoDto.setTitle(mediaJob.getString("caption"));
                        videoInfoDto.setAuthor(pageDataOb.getJSONObject("user").getString("name"));
                        videoInfoDto.setAvatar(pageDataOb.getJSONObject("user").getString("avatar"));
                        videoInfoDto.setCover(mediaJob.getString("shareCover"));
                        videoInfoDto.setTime(pageDataOb.getJSONObject("rawPhoto").getString("timestamp"));
                    }
                }

            }
        }
        Asserts.urlInfoNotNull(videoInfoDto, "解析异常");
        return videoInfoDto;
    }

    @Override
    public VideoInfoDto phpParsingVideoInfo(String url) {
        url = "https://video.rzk.controller.top/?url=" + url;
        String htmlContent = restTemplateUtil.getForObject(url, null, String.class);
        Asserts.urlInfoNotNull(htmlContent, "视频解析异常");
        VideoInfoDto videoInfoDto = JSONArray.parseObject(htmlContent).getObject("data", VideoInfoDto.class);
        Asserts.urlInfoNotNull(videoInfoDto, "视频解析异常");
        return videoInfoDto;
    }


    void isContainsStrings(String url) {
        String[] strings = new String[]{"pipix", "douyin", "huoshan", "h5.weishi", "isee.weishi", "weibo.com", "oasis.weibo", "zuiyou",
                "bbq.bilibili", "kuaishou", "quanmin", "moviebase", "hanyuhl", "eyepetizer", "immomo", "vuevideo",
                "xiaokaxiu", "ippzone", "qq.com", "ixigua.com"
        };
        for (String s : strings) {
            if (url.contains(s)) {
                return;
            }
        }
        Asserts.urlParsingFail("链接格式错误");
    }
}
