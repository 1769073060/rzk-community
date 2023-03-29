package com.rzk.controller.toolapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzk.mapper.dao.WxAppParsingInfoMapper;
import com.rzk.pojo.WxAppParsingInfo;
import com.rzk.controller.toolapi.model.support.BaseResponse;
import com.rzk.service.VideoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

/**
 * @author: 小熊
 * @date: 2021/6/9
 * @description:phone 17521111022
 */
@RestController
@RequestMapping("/api/video")
public class VideoController {
    @Resource
    WxAppParsingInfoMapper parsingInfoMapper;
    @Resource
    VideoService videoService;

    /***
     * 视频无水印链接解析
     * @param url 分享地址
     * @param openId 用户openId
     * @return
     */
    @PostMapping(value = "getVideoInfo")
    public BaseResponse getVideoInfo(@RequestParam(value = "url") String url, @RequestParam(value = "openId") String openId) {
        return BaseResponse.ok(videoService.getVideoInfo(openId, url));
    }

    /***
     * 获取解析记录
     * @param openId
     * @return
     */
    @PostMapping(value = "getParsingInfo")
    public BaseResponse<List> getVideoInfo(@RequestParam(value = "openId") String openId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - 30);
        QueryWrapper<WxAppParsingInfo> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(WxAppParsingInfo::getUserOpenId, openId).gt(WxAppParsingInfo::getCreateTime, calendar.getTime());
        List<WxAppParsingInfo> parsingInfoList = parsingInfoMapper.selectList(queryWrapper);
        return BaseResponse.ok(parsingInfoList);
    }
}
