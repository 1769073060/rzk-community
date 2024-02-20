package com.rzk.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rzk.pojo.consts.WxConsts;
import com.rzk.pojo.consts.WxResourcesConsts;
import com.rzk.pojo.wxserver.*;
import com.rzk.service.IReplyMessageService;
import com.rzk.service.IReplyMessageWCRService;
import com.rzk.service.IWxResourceService;
import com.rzk.service.IWxUserService;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @PackageName : com.rzk.service
 * @FileName : ReplyMessageService
 * @Description : 处理回复消息
 * @Author : rzk
 * @CreateTime : 24/1/2022 上午1:50
 * @Version : v1.0
 */
@DS("slave")
@Service
public class ReplyMessageWCRServiceImpl implements IReplyMessageWCRService {

    private Logger logger = LoggerFactory.getLogger(ReplyMessageWCRServiceImpl.class);
    @Resource
    private IWxResourceService iWxResourceService;
    @Resource
    private IWxUserService iWxUserService;
    private final static String notResourceContent = "该资源不存在,或已失效,可联系我补上该资源!";




    public BaseMessage replyTextMessageWeChatRobot(Map<String, String> requestMap) {
        QueryWrapper<WxResource> queryWrapper = new QueryWrapper();
        QueryWrapper<WxResource> queryWrapperDirectoryName = new QueryWrapper();
        QueryWrapper<WxResource> queryWrapperRes = new QueryWrapper();
        QueryWrapper<WxResource> queryWrapperKk = new QueryWrapper();
        QueryWrapper<WxResource> queryWrapperUrl = new QueryWrapper();
        QueryWrapper<WxResource> wxQueryWrapper = new QueryWrapper();
        QueryWrapper<WxResource> zygxQueryWrapper = new QueryWrapper();
        QueryWrapper<WxResource> uczygxQueryWrapper = new QueryWrapper();
        WxResource wxResourceLzy = null;
        WxResource wxResourceBdy = null;
        WxResource wxResourceTyy = null;
        WxResource wxResourceAly = null;
        WxResource wxResource123p = null;
        WxResource wxResourceKk = null;
        StringBuffer stringBuffer = new StringBuffer();
        TextMessage textMessage = null;
        ImageMessage imageMessage = null;
        //用户发来的内容
        String msg = requestMap.get("Content");

        if (msg != null && !"".equals(msg)) {

            if (msg.equals("录屏工具") || msg.equals("录屏")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "录屏工具提示语"));
                textMessage = textMessage(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("pandownload") || msg.equals("PanDownload")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "PanDownload提示语"));
                textMessage = textMessage(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("小说")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "小说提示语"));
                textMessage = textMessage(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("office") || msg.equals("Office")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "office提示语"));
                textMessage = textMessage(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("ps") || msg.equals("PS") || msg.equals("PS")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "ps提示语"));
                textMessage = textMessage(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("Adobe") || msg.equals("adobe")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "adobe提示语"));
                textMessage = textMessage(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("录屏录像") || msg.equals("屏幕录像")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "屏幕录像提示语"));
                textMessage = textMessage(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("虚拟机")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "虚拟机提示语"));
                textMessage = textMessage(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }

            if (msg.equals("win10家庭版激活码")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "win10家庭版激活码"));

                if (wxResourceLzy != null) {
                    stringBuffer.append(wxResourceLzy.getUrl() + "\n");
                    stringBuffer.append("\n");
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("win10激活码") || msg.equals("win激活码") || msg.equals("windows激活码")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "win10家庭版激活码提示语"));
                textMessage = textMessage(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }

            if (msg.equals("win10")) {
                stringBuffer.append("你是在找win10激活码吗：\n");
                stringBuffer.append("可输入\n");
                stringBuffer.append("win10家庭版激活码\n");
                stringBuffer.append("获取");
                textMessage = new TextMessage(requestMap, stringBuffer.toString());
            }
            if (msg.equals("idea开发工具") || msg.equals("idea")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "idea开发工具提示语"));
                textMessage = textMessage(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("idea激活码")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "idea激活码"));
                textMessage = textMessage(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("谢啦") || msg.equals("谢谢") || msg.equals("谢了") || msg.equals("多谢")) {
                stringBuffer.append("不客气~");
                textMessage = new TextMessage(requestMap, stringBuffer.toString());

            }
            if (msg.equals("你好") || msg.equals("好") || msg.equals("你好啊") || msg.equals("你好呀")) {
                stringBuffer.append("好好好，都很好呢");
                textMessage = new TextMessage(requestMap, stringBuffer.toString());
            }
            if (msg.equals("小程序") || msg.equals("壁纸")) {
                stringBuffer.append("#小程序://共享工具库/3C1QrqGk0dJBROx");
                textMessage = new TextMessage(requestMap, stringBuffer.toString());
            }
            //游戏喜加二
            if (msg.equals("密码") || msg.equals("解压密钥") || msg.equals("解压码")
                    ||   msg.equals("解压密码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "解压密码"));

                //stringBuffer.append("<a href=\"" + wxResource.getUrl() + "\">免费领取链接</a>");
                stringBuffer.append("解压密码：" + wxResource.getUrl() );
                textMessage = new TextMessage(requestMap, stringBuffer.toString());
            }
            if (msg.equals("webstrom") || msg.equals("webstorm")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "webstorm开发工具提示语"));
                textMessage = textMessage(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }


            //解压软件
            if (msg.equals("优酷") || msg.equals("优酷会员") || msg.equals("优酷vip会员")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "优酷一日vip会员"));

                //stringBuffer.append("<a href=\"" +
                       // wxResource.getUrl() +
                      //  "\">点击领取</a>");
                stringBuffer.append("优酷一日vip会员（活动已过期）");
                textMessage = new TextMessage(requestMap, stringBuffer.toString());
            }

            //解压软件
            if (msg.equals("NanaZip") || msg.equals("nanazip") || msg.equals("Nanazip") || msg.equals("Namazip") || msg.equals("NamaZip")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "NanaZip解压缩软件蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "NanaZip解压缩软件百度云"));
                wxResourceKk = iWxResourceService.getOne(queryWrapperKk.eq("file_name", "NanaZip解压缩软件夸克"));
                if (wxResourceLzy != null && wxResourceBdy != null && wxResourceKk != null) {
                    textMessage = textMessageThree(wxResourceBdy, wxResourceLzy, wxResourceKk, stringBuffer, textMessage, requestMap);

                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }

            //洛雪音乐播放器
            if (msg.equals("洛雪音乐播放器") || msg.equals("LXMusic2")) {
                wxResource123p = iWxResourceService.getOne(queryWrapper.eq("file_name", "洛雪音乐播放器123盘"));
                wxResourceLzy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "洛雪音乐播放器蓝奏云"));
                if (wxResourceLzy != null && wxResource123p != null) {
                    textMessage = textMessageTwo(wxResource123p, wxResourceLzy, stringBuffer, textMessage, requestMap);

                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //福昕格式转换工具
            if (msg.equals("福昕格式转换工具") || msg.equals("福昕CAD转换器") || msg.equals("福昕转换器")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "福昕CAD转换器蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "福昕CAD转换器百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);

                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //MovaviVideo21
            if (msg.equals("MovaviVideo21") || msg.equals("MovaviVideo2021") || msg.equals("movavivideo2021") || msg.equals("movavivideo21") || msg.equals("NamaZip")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "movavivideo21蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "movavivideo21百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);

                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //思维脑图
            if (msg.equals("DesktopNaotu 或 桌面脑图") || msg.equals("naotu") || msg.equals("desktopnaotu") || msg.equals("DesktopNaotu") || msg.equals("桌面脑图")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "桌面脑图思维导图DesktopNaotu蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "桌面脑图思维导图DesktopNaotu百度云"));
                wxResourceKk = iWxResourceService.getOne(queryWrapperKk.eq("file_name", "桌面脑图思维导图DesktopNaotu夸克"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageThree(wxResourceBdy, wxResourceLzy, wxResourceKk, stringBuffer, textMessage, requestMap);

                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //在线翻译软件
            if (msg.equals("打字音效 或 tickeys") || msg.equals("打字音效") || msg.equals("tickeys") || msg.equals("Tickeys")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "打字音效tickeys蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "打字音效tickeys百度云"));
                wxResourceKk = iWxResourceService.getOne(queryWrapperKk.eq("file_name", "打字音效tickeys夸克"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageThree(wxResourceBdy, wxResourceLzy, wxResourceKk, stringBuffer, textMessage, requestMap);

                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //visualstudio2022
            if (msg.equals("VisualStudio2022")
                    || msg.equals("vs2022")
                    || msg.equals("visualstudio2022")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "visualstudio2022蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "visualstudio2022百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);

                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //visualstudio2022
            if (msg.equals("VisualStudio2019")
                    || msg.equals("vs2019")
                    || msg.equals("visualstudio2019")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "visualstudio2019蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "visualstudio2019百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);

                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //微信检测软件
            if (msg.equals("微信检测")
                    || msg.equals("微信检测好友")
                    || msg.equals("微信好友检测")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "微信检测好友蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "微信检测好友百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);

                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //文件对比软件
            if (msg.equals("搜题")
                    || msg.equals("搜题神器")
                    || msg.equals("搜题生气")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "搜题神器蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "搜题神器百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //文件对比软件
            if (msg.equals("BeyondCompare")
                    || msg.equals("beyondCompare")
                    || msg.equals("Beyond Compare")
                    || msg.equals("Beyondcompare")
                    || msg.equals("beyondcompare")
                    || msg.equals("beyond compare")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "BeyondCompare蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "BeyondCompare百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);

                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //在线翻译软件
            if (msg.equals("分流抢票软件") || msg.equals("抢票软件") || msg.equals("bypass") || msg.equals("Bypass")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "分流抢票Bypass蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "分流抢票Bypass百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);

                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //在线翻译软件
            if (msg.equals("CopyTranslator") || msg.equals("copytranslator") || msg.equals("Copytranslator")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "CopyTranslator蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "CopyTranslator百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);

                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }

            //鼠标美化工具
            if (msg.equals("CustomCursor") || msg.equals("customcursor") || msg.equals("Customcursor")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "CustomCursor蓝奏云"));
                if (wxResourceLzy != null) {
                    textMessage = textMessageLzy(wxResourceLzy, stringBuffer, textMessage, requestMap);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //Markdown 编辑器
            if (msg.equals("Typora") || msg.equals("typora") || msg.equals("TYPORA")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "typora蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "typora百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //数据库管理工具
            if (msg.equals("Krita") || msg.equals("krita")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "krita百度云"));
                textMessage = textMessageBdy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            //数据库管理工具
            if (msg.equals("wps2022") || msg.equals("WPS2022")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "wps2022阿里云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "wps2022百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    stringBuffer.append(wxResourceLzy.getFileName() + "\n");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResourceLzy.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResourceLzy.getFetchCode() + "\n");
                    stringBuffer.append("\n");
                    stringBuffer.append(wxResourceBdy.getFileName() + "\n");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResourceBdy.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResourceBdy.getFetchCode());
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //思维导图便携版
            if (msg.equals("微信验证码") || msg.equals("验证码")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "验证码"));
                if (wxResourceBdy != null) {
                    stringBuffer.append("微信验证码:");
                    stringBuffer.append(wxResourceBdy.getContent());
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //数据库管理工具
            if (msg.equals("navicat12") || msg.equals("Navicat12")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "navicat12蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "navicat12百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //桌面日程倒计时应用
            if (msg.equals("CountBoard") || msg.equals("countboard")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "CountBoard蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "CountBoard百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //思维导图便携版
            if (msg.equals("Xmind2022便携版") || msg.equals("XMind2022便携版") || msg.equals("xmind2022便携版")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "XMind2022便携版"));
                textMessage = textMessageBdy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            //思维导图
            if (msg.equals("Xmind2022") || msg.equals("XMind2022") || msg.equals("xmind2022")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "XMind2022"));
                textMessage = textMessageBdy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            //win11
            if (msg.equals("win11") || msg.equals("windows11") || msg.equals("Windows11")) {
                stringBuffer.append("Windows11迅雷下载链接:");
                stringBuffer.append("\n");
                stringBuffer.append("magnet:?xt=urn:btih:d10180a7b9c331d73badfad18b77418ba754252c&dn=zh-cn_windows_11_consumer_editions_updated_april_2022_x64_dvd_1f8b4956.iso&xl=5741033472");
                stringBuffer.append("\n");
                stringBuffer.append("打开迅雷软件复制以上地址,即可下载");
                textMessage = new TextMessage(requestMap, stringBuffer.toString());
            }
            //DesktopGoose
            if (msg.equals("7-Zip") || msg.equals("7-zip") || msg.equals("7zip") || msg.equals("7_zip")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "7-Zip"));
                textMessage = textMessageLzy(wxResourceLzy, stringBuffer, textMessage, requestMap);
            }
            //DesktopGoose
            if (msg.equals("DesktopGoose") || msg.equals("desktopgoose") || msg.equals("桌面宠物")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "DesktopGoose"));
                textMessage = textMessageLzy(wxResourceLzy, stringBuffer, textMessage, requestMap);
            }
            //RustDesk远程连接
            if (msg.equals("RustDesk") || msg.equals("rustdesk")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "RustDesk"));
                textMessage = textMessageLzy(wxResourceLzy, stringBuffer, textMessage, requestMap);
            }
            //wps2023
            if (msg.equals("wps2023app") || msg.equals("wps2023手机版")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "wps2023app"));
                textMessage = textMessageLzy(wxResourceLzy, stringBuffer, textMessage, requestMap);
            }
            //游戏喜加二
            if (msg.equals("【喜加二】") || msg.equals("消逝的光芒") || msg.equals("游戏领取") || msg.equals("喜加二")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "喜加二"));

                stringBuffer.append("<a href=\"" +
                        wxResource.getUrl() +
                        "\">免费领取链接</a>");
                textMessage = new TextMessage(requestMap, stringBuffer.toString());
            }

            if (msg.equals("百度云盘") || msg.equals("百度网盘")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "百度网盘"));

                stringBuffer.append("<a href=\"" +
                        wxResource.getUrl() +
                        "\">领取链接</a>");
                textMessage = new TextMessage(requestMap, stringBuffer.toString());

            }
            //抖音特效软件
            if (msg.equals("像塑") || msg.equals("抖音特效软件")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "像塑"));
                textMessage = textMessageLzy(wxResourceLzy, stringBuffer, textMessage, requestMap);
            }
            //墨墨背单词
            if (msg.equals("墨墨背单词") || msg.equals("墨墨背单词破解版") || msg.equals("墨墨背单词app")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "墨墨背单词百度云"));
                textMessage = textMessageBdy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            //护眼宝
            if (msg.equals("护眼宝") || msg.equals("护眼") || msg.equals("护眼软件")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "护眼宝蓝奏云"));
                textMessage = textMessageLzy(wxResourceLzy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("IDEA2022") || msg.equals("Idea2022") || msg.equals("idea2022")) {
                wxResourceTyy = iWxResourceService.getOne(queryWrapper.eq("file_name", "idea2022天翼云"));
                System.out.println(wxResourceTyy);

                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "idea2022百度云"));
                System.out.println(wxResourceBdy);

                wxResourceLzy = iWxResourceService.getOne(queryWrapperUrl.eq("file_name", "idea2022下载地址"));
                System.out.println(wxResourceLzy);
                if (wxResourceTyy != null && wxResourceBdy != null) {
                    System.out.println(wxResourceLzy);

                    stringBuffer.append(wxResourceTyy.getFileName() + "\n");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResourceTyy.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResourceTyy.getFetchCode() + "\n");
                    stringBuffer.append("\n");
                    stringBuffer.append(wxResourceBdy.getFileName() + "\n");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResourceBdy.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResourceBdy.getFetchCode());
                    stringBuffer.append("\n");
                    stringBuffer.append("idea2022下载地址:" + wxResourceLzy.getUrl());

                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("IDEA2021") || msg.equals("Idea2021") || msg.equals("idea2021")) {
                wxResourceTyy = iWxResourceService.getOne(queryWrapper.eq("file_name", "idea2021天翼云"));
                System.out.println(wxResourceTyy);

                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "idea2021百度云"));
                System.out.println(wxResourceBdy);

                wxResourceLzy = iWxResourceService.getOne(queryWrapperUrl.eq("file_name", "idea2021下载地址"));
                System.out.println(wxResourceLzy);
                if (wxResourceTyy != null && wxResourceBdy != null) {
                    System.out.println(wxResourceLzy);

                    stringBuffer.append(wxResourceTyy.getFileName() + "\n");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResourceTyy.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResourceTyy.getFetchCode() + "\n");
                    stringBuffer.append("\n");
                    stringBuffer.append(wxResourceBdy.getFileName() + "\n");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResourceBdy.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResourceBdy.getFetchCode());
                    stringBuffer.append("\n");
                    stringBuffer.append("idea2021下载地址:" + wxResourceLzy.getUrl());

                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //流程工具
            if (msg.equals("iauto") || msg.equals("iAuto") || msg.equals("IAuto") || msg.equals("Iauto")) {
                wxResourceTyy = iWxResourceService.getOne(queryWrapper.eq("file_name", "IAuto天翼云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "IAuto百度云"));
                if (wxResourceTyy != null && wxResourceBdy != null) {
                    stringBuffer.append(wxResourceTyy.getFileName() + "\n");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResourceTyy.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResourceTyy.getFetchCode() + "\n");
                    stringBuffer.append("\n");
                    stringBuffer.append(wxResourceBdy.getFileName() + "\n");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResourceBdy.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResourceBdy.getFetchCode());
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //小型ps
            if (msg.equals("Pinta") || msg.equals("pinta") || msg.equals("PINTA")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "pinta"));
                textMessage = textMessageLzy(wxResourceLzy, stringBuffer, textMessage, requestMap);
            }
            //笔趣阁9
            if (msg.equals("笔趣阁9") ) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "笔趣阁9"));
                textMessage = textMessageLzy(wxResourceLzy, stringBuffer, textMessage, requestMap);
            }
            //黑苹果
            if (msg.equals("黑苹果") || msg.equals("黑苹果镜像") || msg.equals("ios镜像")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "黑苹果镜像"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "黑苹果镜像解锁工具"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    stringBuffer.append(wxResourceLzy.getFileName() + "\n");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResourceLzy.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResourceLzy.getFetchCode() + "\n");
                    stringBuffer.append("\n");
                    stringBuffer.append(wxResourceBdy.getFileName() + "\n");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResourceBdy.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResourceBdy.getFetchCode());
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //Office自定义安装工具
            if (msg.equals("Office2021") || msg.equals("office2021") || msg.equals("office2020") || msg.equals("office2019") ) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "Office自定义安装工具蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "Office自定义安装工具百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //Office自定义安装工具
            if (msg.equals("Office自定义安装工具") || msg.equals("Office安装工具") || msg.equals("Office安装组件") || msg.equals("Office组件") || msg.equals("Office自定义组件安装工具")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "Office自定义安装工具蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "Office自定义安装工具百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //屏幕录像专家
            if (msg.equals("Capturea") || msg.equals("capturea") || msg.equals("capture") || msg.equals("captura")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "Captura蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "Captura百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //VirtualBox
            if (msg.equals("virtualbox") || msg.equals("VirtualBox") || msg.equals("VirtualBox")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "VirtualBox"));
                textMessage = textMessageBdy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }

            //VMware
            if (msg.equals("vmware") || msg.equals("vmware16") || msg.equals("VMware16") || msg.equals("VMware")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "VMware"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getFileName() + "\n");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResource.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResource.getFetchCode());
                    stringBuffer.append("\n");
                    stringBuffer.append("\n");
                    stringBuffer.append("<a href=\"" +
                            "https://mp.weixin.qq.com/s?__biz=Mzk0MzMyMTI3Mg==&mid=2247484486&idx=1&sn=b91cef18d71b18c18af9b55acc0429d5&chksm=c334fd91f4437487e2ffef0b738ef8179b69ce5353513d4645b1edaad58ffcf5e8d7cfe90b93#rd" +
                            "\">图文教程</a>");
                    stringBuffer.append("\n");
                    stringBuffer.append("\n");
                    stringBuffer.append("<a href=\"" +
                            "https://www.bilibili.com/video/BV13F41147o2?spm_id_from=333.999.0.0" +
                            "\">视频教程</a>");
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //PanDownload蓝奏云
            if (msg.equals("pandownload公测版") || msg.equals("PanDownload公测版 ") || msg.equals("PanDownload公测版")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "PanDownload蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "PanDownload百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //屏幕录像专家
            if (msg.equals("掌上英语四级1") || msg.equals("掌上英语四级1.0") || msg.equals("掌上英语1.0")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "掌上英语四级蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "掌上英语四级百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            //屏幕录像专家
            if (msg.equals("屏幕录像专家2022") || msg.equals("屏幕录像2022") || msg.equals("录屏录像专家") || msg.equals("屏幕录像专家")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "屏幕录像专家蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "屏幕录像专家百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }

            /**
             * 淘宝运营资料
             */
            if (msg.equals("tb教程") || msg.equals("淘宝运营教程") || msg.equals("tbjc")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "淘宝教程"));
                textMessage = textMessageLzy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            /**
             * 白猿小说神器
             */
            if (msg.equals("白猿搜书") || msg.equals("白猿搜书小说") || msg.equals("白猿小说")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "白猿搜书蓝奏云"));
                textMessage = textMessageLzy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("白猿搜书备用") || msg.equals("白猿搜书小说备用") || msg.equals("白猿小说备用")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "白猿搜书百度云"));
                textMessage = textMessageLzy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            /**
             * 论文查重软件win
             */
            if (msg.equals("Paperpass") || msg.equals("论文查重软件") || msg.equals("论文查重") || msg.equals("Paperpass,论文查重软件,论文查重")) {
                wxResourceLzy = iWxResourceService.getOne(queryWrapper.eq("file_name", "Paperpass蓝奏云"));
                wxResourceBdy = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "Paperpass百度云"));
                if (wxResourceLzy != null && wxResourceBdy != null) {
                    textMessage = textMessageTwo(wxResourceBdy, wxResourceLzy, stringBuffer, textMessage, requestMap);

                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            /**
             * FSC屏幕捕抓工具(Windows)
             */
            if (msg.equals("FSC屏幕捕抓工具") || msg.equals("fsc屏幕捕抓工具")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "FSC屏幕捕抓工具"));
                textMessage = textMessageLzy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("fsc二维码") || msg.equals("FSC二维码") || msg.equals("FSC图") || msg.equals("fsc") || msg.equals("FSC") || msg.equals("fsc图")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "FSC屏幕捕抓工具"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Office_Media_Id_2010);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            /**
             * office2010
             */
            if (msg.equals("office2010") || msg.equals("Office2010") || msg.equals("office10")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "office2010"));
                textMessage = textMessageLzy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("office10二维码") ||
                    msg.equals("office10版二维码") ||
                    msg.equals("office10图") ||
                    msg.equals("office2010二维码") ||
                    msg.equals("office2010版二维码") ||
                    msg.equals("office2010图")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "office2010"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Office_Media_Id_2010);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * office2016
             */
            if (msg.equals("office2016") || msg.equals("Office2016") || msg.equals("office16")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "office2016"));
                textMessage = textMessageLzy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("office16二维码") ||
                    msg.equals("office16版二维码") ||
                    msg.equals("office16图") ||
                    msg.equals("office2016二维码") ||
                    msg.equals("office2016版二维码") ||
                    msg.equals("office2016图")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "office2016"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Office_Media_Id_2016);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            /**
             * office2019
             */
            if (msg.equals("office2019") || msg.equals("Office2019") || msg.equals("office19")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "office2019"));
                textMessage = textMessageLzy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("office19二维码") ||
                    msg.equals("office19版二维码") ||
                    msg.equals("office19图") ||
                    msg.equals("office2019二维码") ||
                    msg.equals("office2019版二维码") ||
                    msg.equals("office2019图")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "office2019"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Office_Media_Id_2019);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            /**
             * office365
             */
            if (msg.equals("office365")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "office365"));
                textMessage = textMessageLzy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("office365二维码") ||
                    msg.equals("office19版二维码") ||
                    msg.equals("office365图")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "office365"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Office_Media_Id_365);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            /**
             * Adobe2022全家桶
             */
            if (msg.equals("Adobe2022") ||
                    msg.equals("adobe2022") ||
                    msg.equals("adobe2022全家桶") ||
                    msg.equals("Adobe2022全家桶") ||
                    msg.equals("adobe22")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "Adobe2022"));
                textMessage = textMessageLzy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("Adobe2022二维码") ||
                    msg.equals("Adobe2022版二维码") ||
                    msg.equals("Adobe2022图") ||
                    msg.equals("adobe2022二维码") ||
                    msg.equals("adobe2022版二维码") ||
                    msg.equals("adobe2022图")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "Adobe2022"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_2022);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * Adobe2019全家桶
             */
            if (msg.equals("Adobe2019") ||
                    msg.equals("adobe2019") ||
                    msg.equals("adobe2019全家桶") ||
                    msg.equals("Adobe2019全家桶") ||
                    msg.equals("adobe19")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "Adobe2019"));
                textMessage = textMessageLzy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("Adobe2019二维码") ||
                    msg.equals("Adobe2019版二维码") ||
                    msg.equals("Adobe2019图") ||
                    msg.equals("adobe2019二维码") ||
                    msg.equals("adobe2019版二维码") ||
                    msg.equals("adobe2019图")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "Adobe2019"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_2019);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * Adobe2018mac全家桶
             */
            if (msg.equals("Adobe2018mac") || msg.equals("adobe2018mac")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "Adobe2018mac"));
                textMessage = textMessageLzy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("Adobe2018mac二维码") || msg.equals("adobe2018mac二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "Adobe2018mac"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_2018_Mac);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * Adobe2019mac全家桶
             */
            if (msg.equals("Adobe2019mac") || msg.equals("adobe2019mac")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "Adobe2019mac"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getFileName() + "全家桶");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResource.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResource.getFetchCode());
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("Adobe2019mac二维码") || msg.equals("adobe2019mac二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "Adobe2019mac"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_2019_Mac);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobePSCC
             */
            if (msg.equals("PSCC") || msg.equals("psCC") || msg.equals("pscc")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePsCC"));
                textMessage = textMessageLzy(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("PSCC二维码") || msg.equals("psCC二维码") || msg.equals("pscc二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePsCC"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_PS_CC);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobePSCC2022
             */
            if (msg.equals("PS2023") || msg.equals("Ps2023") || msg.equals("ps2023") || msg.equals("PS2023") || msg.equals("ps2023")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "ps2023百度云"));
                wxResourceAly = iWxResourceService.getOne(queryWrapperRes.eq("file_name", "ps2023阿里云"));
                if (wxResourceAly != null && wxResourceBdy != null) {
                    stringBuffer.append(wxResourceBdy.getFileName() + "\n");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResourceBdy.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResourceBdy.getFetchCode() + "\n");
                    stringBuffer.append("\n");
                    stringBuffer.append(wxResourceAly.getFileName() + "\n");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResourceAly.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResourceAly.getFetchCode() + "\n" + "\n");
                    stringBuffer.append(wxResourceBdy.getArticleAddresses() != null || wxResourceAly.getArticleAddresses() != null ? "使用教程" +
                            wxResourceBdy.getArticleAddresses()
                            :
                            "");
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }

            /**
             * AdobePSCC2022
             */
            if (msg.equals("PSCC2022") || msg.equals("psCC2022") || msg.equals("pscc2022") || msg.equals("PS2022") || msg.equals("ps2022")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePsCC2022"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getFileName() + "\n");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResource.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResource.getFetchCode());
                    stringBuffer.append("\n");
                    stringBuffer.append("<a href=\"" +
                            "https://mp.weixin.qq.com/s?__biz=Mzk0MzMyMTI3Mg==&mid=2247483787&idx=1&sn=87224d3121945de2f0b72a063c39b96e&chksm=c334f85cf443714a514d8758008e49008c9cb1cda7c020ca648db15ee6f0de803681a6d74af4&token=1596680842&lang=zh_CN#rd" +
                            "\">图文教程</a>");
                    stringBuffer.append("\n");
                    stringBuffer.append("<a href=\"" +
                            "https://www.bilibili.com/video/BV16Z4y1k7Lx?spm_id_from=333.999.0.0" +
                            "\">视频教程</a>");
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("PSCC2022二维码") || msg.equals("psCC2022二维码") || msg.equals("ps2022二维码") || msg.equals("pscc2022二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePsCC2022"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_PS_CC_2022);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobePSCC2020
             */
            if (msg.equals("PSCC2020") || msg.equals("psCC2020") || msg.equals("pscc2020") || msg.equals("ps2020")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePsCC2020"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getFileName() + "\n");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResource.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResource.getFetchCode());
                    stringBuffer.append("\n");
                    stringBuffer.append("<a href=\"" +
                            "https://mp.weixin.qq.com/s?__biz=Mzk0MzMyMTI3Mg==&mid=2247483787&idx=1&sn=87224d3121945de2f0b72a063c39b96e&chksm=c334f85cf443714a514d8758008e49008c9cb1cda7c020ca648db15ee6f0de803681a6d74af4&token=1596680842&lang=zh_CN#rd" +
                            "\">图文教程</a>");
                    stringBuffer.append("\n");
                    stringBuffer.append("<a href=\"" +
                            "https://www.bilibili.com/video/BV16Z4y1k7Lx?spm_id_from=333.999.0.0" +
                            "\">视频教程</a>");
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("PSCC2020二维码") || msg.equals("psCC2020二维码") || msg.equals("ps2020二维码") || msg.equals("pscc2020二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePsCC2020"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_PS_CC_2020);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobePSCC2019
             */
            if (msg.equals("PSCC2019") || msg.equals("psCC2019") || msg.equals("pscc2019") || msg.equals("ps2019")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePsCC2019"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getFileName() + "\n");
                    stringBuffer.append("链接:");
                    stringBuffer.append(wxResource.getUrl() + "\n");
                    stringBuffer.append("提取码:");
                    stringBuffer.append(wxResource.getFetchCode());
                    stringBuffer.append("\n");
                    stringBuffer.append("<a href=\"" +
                            "https://mp.weixin.qq.com/s?__biz=Mzk0MzMyMTI3Mg==&mid=2247483787&idx=1&sn=87224d3121945de2f0b72a063c39b96e&chksm=c334f85cf443714a514d8758008e49008c9cb1cda7c020ca648db15ee6f0de803681a6d74af4&token=1596680842&lang=zh_CN#rd" +
                            "\">图文教程</a>");
                    stringBuffer.append("\n");
                    stringBuffer.append("<a href=\"" +
                            "https://www.bilibili.com/video/BV16Z4y1k7Lx?spm_id_from=333.999.0.0" +
                            "\">视频教程</a>");
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("PSCC2019二维码") || msg.equals("psCC2019二维码") || msg.equals("ps2019二维码") || msg.equals("pscc2019二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePsCC2019"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_PS_CC_2019);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobePSCC2018
             */
            if (msg.equals("PSCC2018") || msg.equals("psCC2018") || msg.equals("pscc2018") || msg.equals("ps2018")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePsCC2018"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("PSCC2018二维码") || msg.equals("psCC2018二维码") || msg.equals("ps2018二维码") || msg.equals("pscc2018二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePsCC2018"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_PS_CC_2018);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobePSCC2015
             */
            if (msg.equals("PSCC2015") || msg.equals("psCC2015") || msg.equals("pscc2015") || msg.equals("ps2015")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePsCC2015"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("PSCC2015二维码") || msg.equals("psCC2015二维码") || msg.equals("ps2015二维码") || msg.equals("pscc2015二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePsCC2015"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_PS_CC_2015);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobePSCC2014
             */
            if (msg.equals("PSCC2014") || msg.equals("psCC2014") || msg.equals("pscc2014") || msg.equals("ps2014")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePsCC2014"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("PSCC2014二维码") || msg.equals("psCC2014二维码") || msg.equals("ps2014二维码") || msg.equals("pscc2014二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePsCC2014"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_PS_CC_2014);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /**
             * AdobePRCC
             */
            if (msg.equals("PRCC") || msg.equals("prCC") || msg.equals("prcc")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePrCC"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("PRCC二维码") || msg.equals("prCC二维码") || msg.equals("prcc二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePrCC"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_PR_CC);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobePRCC2022
             */
            if (msg.equals("PRCC2022") || msg.equals("prCC2022") || msg.equals("prcc2022") || msg.equals("PR2022") || msg.equals("pr2022")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePrCC2022"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("PRCC2022二维码") || msg.equals("prCC2022二维码") || msg.equals("pr2022二维码") || msg.equals("prcc2022二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePrCC2022"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_PR_CC_2022);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobePRCC2020
             */
            if (msg.equals("PRCC2020") || msg.equals("prCC2020") || msg.equals("prcc2020") || msg.equals("pr2020")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePrCC2020"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("PRCC2020二维码") || msg.equals("prCC2020二维码") || msg.equals("pr2020二维码") || msg.equals("prcc2020二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePrCC2020"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_PR_CC_2020);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobePRCC2019
             */
            if (msg.equals("PRCC2019") || msg.equals("prCC2019") || msg.equals("prcc2019") || msg.equals("pr2019")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePrCC2019"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            if (msg.equals("PRCC2019二维码") || msg.equals("prCC2019二维码") || msg.equals("pr2019二维码") || msg.equals("prcc2019二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePrCC2019"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_PR_CC_2019);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobePRCC2018
             */
            if (msg.equals("PRCC2018") || msg.equals("prCC2018") || msg.equals("prcc2018") || msg.equals("pr2018")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePrCC2018"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("PRCC2018二维码") || msg.equals("prCC2018二维码") || msg.equals("pr2018二维码") || msg.equals("prcc2018二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePrCC2018"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_PR_CC_2018);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobePRCC2015
             */
            if (msg.equals("PRCC2015") || msg.equals("prCC2015") || msg.equals("prcc2015") || msg.equals("pr2015")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePrCC2015"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("PRCC2015二维码") || msg.equals("prCC2015二维码") || msg.equals("pr2015二维码") || msg.equals("prcc2015二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePrCC2015"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_PR_CC_2015);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobePSCC2014
             */
            if (msg.equals("PRCC2014") || msg.equals("prCC2014") || msg.equals("prcc2014") || msg.equals("pr2014")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePrCC2014"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("PRCC2014二维码") || msg.equals("prCC2014二维码") || msg.equals("pr2014二维码") || msg.equals("prcc2014二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobePrCC2014"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_PR_CC_2014);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            /**
             * AdobeLRCC2022
             */
            if (msg.equals("LRCC2022") || msg.equals("lrCC2022") || msg.equals("lrcc2022") || msg.equals("LR2022") || msg.equals("lr2022")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeLrCC2022"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("LRCC2022二维码") || msg.equals("lrCC2022二维码") || msg.equals("lr2022二维码") || msg.equals("lrcc2022二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeLrCC2022"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_LR_CC_2022);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobeLrCC2019
             */
            if (msg.equals("LRCC2019") || msg.equals("lrCC2019") || msg.equals("lrcc2019") || msg.equals("lr2019")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeLrCC2019"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("LRCC2019二维码") || msg.equals("lrCC2019二维码") || msg.equals("lr2019二维码") || msg.equals("lrcc2019二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeLrCC2019"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_LR_CC_2019);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobeLRCC2018
             */
            if (msg.equals("LRCC2018") || msg.equals("lrCC2018") || msg.equals("lrcc2018") || msg.equals("lr2018")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeLrCC2018"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("LRCC2018二维码") || msg.equals("lrCC2018二维码") || msg.equals("lr2018二维码") || msg.equals("lrcc2018二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeLrCC2018"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_LR_CC_2018);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobeLRCC2015
             */
            if (msg.equals("LRCC2015") || msg.equals("lrCC2015") || msg.equals("lrcc2015") || msg.equals("lr2015")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeLrCC2015"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }

            if (msg.equals("LRCC2015二维码") || msg.equals("lrCC2015二维码") || msg.equals("lr2015二维码") || msg.equals("lrcc2015二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeLrCC2015"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_LR_CC_2015);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////


            /**
             * AdobeIDCC2022
             */
            if (msg.equals("IDCC2022") || msg.equals("idCC2022") || msg.equals("idcc2022") || msg.equals("ID2022") || msg.equals("id2022")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeIdCC2022"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("IDCC2022二维码") || msg.equals("idCC2022二维码") || msg.equals("id2022二维码") || msg.equals("idcc2022二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeIdCC2022"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_ID_CC_2022);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            /**
             * AdobeIdCC2019
             */
            if (msg.equals("IDCC2019") || msg.equals("idCC2019") || msg.equals("idcc2019") || msg.equals("id2019")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeIdCC2019"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }
            }
            if (msg.equals("IDCC2019二维码") || msg.equals("idCC2019二维码") || msg.equals("id2019二维码") || msg.equals("idcc2019二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeIdCC2019"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_ID_CC_2019);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);

                }

            }
            /**
             * AdobeIDCC2018
             */
            if (msg.equals("IDCC2018") || msg.equals("idCC2018") || msg.equals("idcc2018") || msg.equals("id2018")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeIdCC2018"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            if (msg.equals("IDCC2018二维码") || msg.equals("idCC2018二维码") || msg.equals("id2018二维码") || msg.equals("idcc2018二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeIdCC2018"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_ID_CC_2018);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);

                }

            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////

            /**
             * AdobeDWCC2022
             */
            if (msg.equals("DWCC2022") || msg.equals("dwCC2022") || msg.equals("dwcc2022") || msg.equals("DW2022") || msg.equals("dw2022")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeDwCC2022"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            if (msg.equals("DWCC2022二维码") || msg.equals("dwCC2022二维码") || msg.equals("dw2022二维码") || msg.equals("dwcc2022二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeDwCC2022"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_DW_CC_2022);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);

                }

            }
            /**
             * AdobeDwCC2019
             */
            if (msg.equals("DWCC2019") || msg.equals("dwCC2019") || msg.equals("dwcc2019") || msg.equals("dw2019")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeDwCC2019"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            if (msg.equals("DWCC2019二维码") || msg.equals("dwCC2019二维码") || msg.equals("dw2019二维码") || msg.equals("dwcc2019二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeDwCC2019"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_DW_CC_2019);
                    return new ImageMessage(requestMap, image);
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);

                }

            }
            /**
             * AdobeDWCC2018
             */
            if (msg.equals("DWCC2018") || msg.equals("dwCC2018") || msg.equals("dwcc2018") || msg.equals("dw2018")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeDwCC2018"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, notResourceContent);
                }

            }
            if (msg.equals("DWCC2018二维码") || msg.equals("dwCC2018二维码") || msg.equals("dw2018二维码") || msg.equals("dwcc2018二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeDwCC2018"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_DW_CC_2018);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeDWCC2015
             */
            if (msg.equals("DWCC2015") || msg.equals("dwCC2015") || msg.equals("dwcc2015") || msg.equals("dw2015")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeDwCC2015"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("DWCC2015二维码") || msg.equals("dwCC2015二维码") || msg.equals("dw2015二维码") || msg.equals("dwcc2015二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeDwCC2015"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_DW_CC_2015);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /**
             * AdobeAICC
             */
            if (msg.equals("AICC") || msg.equals("AiCC") || msg.equals("aicc")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAiCC"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AICC二维码") || msg.equals("aiCC二维码") || msg.equals("Aicc二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAiCC"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AI_CC);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }


            }
            /**
             * AdobeAUCC2022
             */
            if (msg.equals("AICC2022") || msg.equals("aiCC2022") || msg.equals("aicc2022") || msg.equals("AI2022") || msg.equals("ai2022")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAiCC2022"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AICC2022二维码") || msg.equals("aiCC2022二维码") || msg.equals("ai2022二维码") || msg.equals("aicc2022二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAiCC2022"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AI_CC_2022);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeAUCC2019
             */
            if (msg.equals("AICC2019") || msg.equals("aiCC2019") || msg.equals("aicc2019") || msg.equals("ai2019")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAiCC2019"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AICC2019二维码") || msg.equals("aiCC2019二维码") || msg.equals("ai2019二维码") || msg.equals("aicc2019二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAiCC2019"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AI_CC_2019);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeAUCC2018
             */
            if (msg.equals("AICC2018") || msg.equals("aiCC2018") || msg.equals("aicc2018") || msg.equals("ai2018")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAiCC2018"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AICC2018二维码") || msg.equals("aiCC2018二维码") || msg.equals("ai2018二维码") || msg.equals("aicc2018二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAiCC2018"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AI_CC_2018);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeAUCC2015
             */
            if (msg.equals("AUCC2015") || msg.equals("aiCC2015") || msg.equals("aicc2015") || msg.equals("ai2015")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAiCC2015"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AICC2015二维码") || msg.equals("aiCC2015二维码") || msg.equals("ai2015二维码") || msg.equals("aicc2015二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAiCC2015"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AI_CC_2015);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeAICC2014
             */
            if (msg.equals("AICC2014") || msg.equals("aiCC2014") || msg.equals("aicc2014") || msg.equals("ai2014")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAiCC2014"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AICC2014二维码") || msg.equals("aiCC2014二维码") || msg.equals("ai2014二维码") || msg.equals("aicc2014二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAiCC2014"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AI_CC_2014);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /**
             * AdobeAUCC
             */
            if (msg.equals("AUCC") || msg.equals("AuCC") || msg.equals("aucc")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAuCC"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AUCC二维码") || msg.equals("auCC二维码") || msg.equals("Aucc二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAuCC"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AU_CC);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeAUCC2022
             */
            if (msg.equals("AUCC2022") || msg.equals("auCC2022") || msg.equals("aucc2022") || msg.equals("AU2022") || msg.equals("au2022")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAuCC2022"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AUCC2022二维码") || msg.equals("auCC2022二维码") || msg.equals("au2022二维码") || msg.equals("aucc2022二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAuCC2022"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AU_CC_2022);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeAUCC2019
             */
            if (msg.equals("AUCC2019") || msg.equals("auCC2019") || msg.equals("aucc2019") || msg.equals("au2019")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAuCC2019"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AUCC2019二维码") || msg.equals("auCC2019二维码") || msg.equals("au2019二维码") || msg.equals("aucc2019二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAuCC2019"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AU_CC_2019);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeAUCC2018
             */
            if (msg.equals("AUCC2018") || msg.equals("auCC2018") || msg.equals("aucc2018") || msg.equals("au2018")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAuCC2018"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AuCC2018二维码") || msg.equals("auCC2018二维码") || msg.equals("au2018二维码") || msg.equals("aucc2018二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAuCC2018"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AU_CC_2018);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeAUCC2015
             */
            if (msg.equals("AUCC2015") || msg.equals("auCC2015") || msg.equals("aucc2015") || msg.equals("au2015")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAuCC2015"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AUCC2015二维码") || msg.equals("auCC2015二维码") || msg.equals("au2015二维码") || msg.equals("aucc2015二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAuCC2015"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AU_CC_2015);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeAUAUCC2014
             */
            if (msg.equals("AUCC2014") || msg.equals("auCC2014") || msg.equals("aucc2014") || msg.equals("au2014")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAuCC2014"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AUCC2014二维码") || msg.equals("auCC2014二维码") || msg.equals("au2014二维码") || msg.equals("aucc2014二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAuCC2014"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AU_CC_2014);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /**
             * AdobeAECC
             */
            if (msg.equals("AECC") || msg.equals("AeCC") || msg.equals("aecc")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAeCC"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AECC二维码") || msg.equals("aeCC二维码") || msg.equals("Aecc二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAeCC"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AE_CC);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeAECC2022
             */
            if (msg.equals("AECC2022") || msg.equals("aeCC2022") || msg.equals("aecc2022") || msg.equals("AE2022") || msg.equals("ae2022")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAeCC2022"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AECC2022二维码") || msg.equals("aeCC2022二维码") || msg.equals("ae2022二维码") || msg.equals("aecc2022二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAeCC2022"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AE_CC_2022);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeAECC2019
             */
            if (msg.equals("AECC2019") || msg.equals("aeCC2019") || msg.equals("aecc2019") || msg.equals("ae2019")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAeCC2019"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AECC2019二维码") || msg.equals("aeCC2019二维码") || msg.equals("ae2019二维码") || msg.equals("aecc2019二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAuCC2019"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AE_CC_2019);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeAECC2018
             */
            if (msg.equals("AECC2018") || msg.equals("aeCC2018") || msg.equals("aecc2018") || msg.equals("ae2018")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAeCC2018"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AeCC2018二维码") || msg.equals("aeCC2018二维码") || msg.equals("ae2018二维码") || msg.equals("aecc2018二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAeCC2018"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AE_CC_2018);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeAUCC2015
             */
            if (msg.equals("AECC2015") || msg.equals("aeCC2015") || msg.equals("aecc2015") || msg.equals("ae2015")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAeCC2015"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AECC2015二维码") || msg.equals("aeCC2015二维码") || msg.equals("ae2015二维码") || msg.equals("aecc2015二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAeCC2015"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AE_CC_2015);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeAECC2014
             */
            if (msg.equals("AECC2014") || msg.equals("aeCC2014") || msg.equals("aecc2014") || msg.equals("ae2014")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAeCC2014"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("AECC2014二维码") || msg.equals("aeCC2014二维码") || msg.equals("ae2014二维码") || msg.equals("aecc2014二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAeCC2014"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AE_CC_2014);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }


            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
            /**
             * AdobeADCC
             */
            if (msg.equals("ADCC") || msg.equals("adCC") || msg.equals("adcc")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAdCC"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("ADCC二维码") || msg.equals("adCC二维码") || msg.equals("adcc二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAdCC"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AD_CC);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeADCC2022
             */
            if (msg.equals("ADCC2022") || msg.equals("adCC2022") || msg.equals("adcc2022") || msg.equals("AD2022") || msg.equals("ad2022")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAdCC2022"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("ADCC2022二维码") || msg.equals("adCC2022二维码") || msg.equals("ad2022二维码") || msg.equals("adcc2022二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAdCC2022"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AD_CC_2022);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeADCC2020
             */
            if (msg.equals("ADCC2020") || msg.equals("adCC2020") || msg.equals("adcc2020") || msg.equals("ad2020")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAdCC2020"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("ADCC2020二维码") || msg.equals("adCC2020二维码") || msg.equals("ad2020二维码") || msg.equals("adcc2020二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAdCC2020"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AD_CC_2022);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeADCC2019
             */
            if (msg.equals("ADCC2019") || msg.equals("adCC2019") || msg.equals("adcc2019") || msg.equals("ad2019")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAdCC2019"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("ADCC2019二维码") || msg.equals("adCC2019二维码") || msg.equals("ad2019二维码") || msg.equals("adcc2019二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAdCC2019"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AD_CC_2019);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeADCC2018
             */
            if (msg.equals("ADCC2018") || msg.equals("adCC2018") || msg.equals("adcc2018") || msg.equals("ad2018")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAdCC2018"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);

                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("ADCC2018二维码") || msg.equals("adCC2018二维码") || msg.equals("ad2018二维码") || msg.equals("adcc2018二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAdCC2018"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AD_CC_2018);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeADCC2015
             */
            if (msg.equals("ADCC2015") || msg.equals("adCC2015") || msg.equals("adcc2015") || msg.equals("ad2015")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAdCC2015"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("ADCC2015二维码") || msg.equals("adCC2015二维码") || msg.equals("ad2015二维码") || msg.equals("adcc2015二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAdCC2015"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AD_CC_2015);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            /**
             * AdobeADCC2014
             */
            if (msg.equals("ADCC2014") || msg.equals("adCC2014") || msg.equals("adcc2014") || msg.equals("ad2014")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAdCC2014"));
                if (wxResource != null) {
                    stringBuffer = stringBufferOne(wxResource);
                    textMessage = new TextMessage(requestMap, stringBuffer.toString());
                } else {
                    textMessage = new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("ADCC2014二维码") || msg.equals("adCC2014二维码") || msg.equals("ad2014二维码") || msg.equals("adcc2014二维码")) {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "AdobeAdCC2014"));
                if (wxResource != null) {
                    stringBuffer.append(wxResource.getQrCode());
                    Image image = new Image();
                    image.setMediaId(WxResourcesConsts.Adobe_Media_Id_AD_CC_2014);
                    return new ImageMessage(requestMap, image);
                } else {
                    return new TextMessage(requestMap, "该资源不存在,或已失效,可联系我补上该资源!");
                }

            }
            if (msg.equals("红包封页") || msg.equals("红包封面") || msg.equals("封面红包") || msg.equals("封面") || msg.equals("封页")) {
                wxResourceBdy = iWxResourceService.getOne(queryWrapper.eq("file_name", "fm"));
                textMessage = textMessage(wxResourceBdy, stringBuffer, textMessage, requestMap);
            }
            if (msg.equals("fm")) {
                stringBuffer.append("最新封面：点击☞#红包封面 领取！");
                stringBuffer.append("\n");
                stringBuffer.append(
                        "「龙年红包封面」领取：<a href=\"" +
                                "https://ssl.gongyi.qq.com/m/wxact/2023_goodthings.html#/index" +
                                "\"> ☞点击领取</a>\n");

                /**
                stringBuffer.append(
                        "东风1：<a href=\"" +
                                "https://shortcn.com/shp1g" +
                                "\"> ☞点击领取</a>\n");
                stringBuffer.append("\n");
                stringBuffer.append(
                        "东风2：<a href=\"" +
                                "https://shortcn.com/19dpv" +
                                "\"> ☞点击领取</a>\n");
                stringBuffer.append("\n");
                stringBuffer.append(
                        "AITO1：<a href=\"" +
                                "https://shortcn.com/kvcff" +
                                "\"> ☞点击领取</a>\n");
                stringBuffer.append("\n");
                stringBuffer.append(
                        "AITO2：<a href=\"" +
                                "https://shortcn.com/ogrkn" +
                                "\"> ☞点击领取</a>\n");
                stringBuffer.append("\n");
                stringBuffer.append(
                        "可口可乐：<a href=\"" +
                                "https://support.weixin.qq.com/cgi-bin/mmsupport-bin/showredpacket?receiveuri=zLjHWoPVHSF&check_type=2&immersiveUIStyle=1&immersivePageBgIsDark=2" +
                                "\"> ☞点击领取</a>\n");
                stringBuffer.append("\n");
                stringBuffer.append(
                        "Angelababy<a href=\"" +
                                "https://sourl.cn/ev9Hmg" +
                                "\"> ☞点击领取</a>\n");
                stringBuffer.append("\n");
                stringBuffer.append(
                        "李现：<a href=\"" +
                                "https://shortcn.com/m6vxp" +
                                "\">李现</a>");
                stringBuffer.append("\n");
                stringBuffer.append("\n");

                stringBuffer.append(
                        "轩尼诗：<a href=\"" +
                                "https://support.weixin.qq.com/cgi-bin/mmsupport-bin/showredpacket?receiveuri=tVwKHdsfLaD&check_type=2#wechat_redirect" +
                                "\">轩尼诗</a>");
                stringBuffer.append("积家」封面： ☞点击领取\n");
                stringBuffer.append("\n");

                stringBuffer.append(
                        "积家」封面： <a href=\"" +
                                "https://support.weixin.qq.com/cgi-bin/mmsupport-bin/showredpacket?receiveuri=djR4I3yS7ZE&check_type=2#wechat_redirect" +
                                "\"> ☞点击领取</a>");
                stringBuffer.append("古驰：轻触 #古驰 抢红包封面，摇一摇抽取！\n");
                stringBuffer.append("\n");

                stringBuffer.append("馥蕾诗：（即日起至1月19日，每天上午11:30发放）轻触#馥蕾诗，在搜索结果内领取。\n");
                stringBuffer.append("\n");
                stringBuffer.append("戴森：#小程序://戴森Dyson/WJ6IM2ZA1Gv6Njf  戴森红包封面，即日起至1月22日，每日上午9:00更新，限量3000-13000，领完即止。\n");
                stringBuffer.append("\n");
                stringBuffer.append("「腾讯公益/小红花」封面（需兑换）：#小程序://腾讯公益/p3GHAEPMeGpgfor\n");
                stringBuffer.append("\n");
                stringBuffer.append("「小丸子」封面（每日答题抽奖/4款）：#小程序://视客眼镜网/05UPErEG5NXMXvE\n");
                stringBuffer.append("\n");
                stringBuffer.append(
                        "「小红象」封面：<a href=\"" +
                                "https://hz.qyfw168.cn/miniapp/luck/hongbao/hebei.html" +
                                "\"> ☞点击领取</a>\n");
                **/

                textMessage = new TextMessage(requestMap, stringBuffer.toString());
                logger.info("返回回复文本消息" + textMessage);
            }
            if (msg.equals("过年倒计时") || msg.equals("元旦倒计时") || msg.equals("国庆倒计时") || msg.equals("倒计时")) {
                // 设置日期2012-12-21
                Calendar c = Calendar.getInstance();
                c.set(2024, 0, 0, 0, 0, 0);//元旦
                c.set(2023, 9, 0, 0, 0, 0);//元旦
                // 单独设置年、月、日、小时、分钟、秒
//        c.set(Calendar.YEAR, 2023);
//        c.set(Calendar.MONTH, Calendar.JANUARY); // 0 表示1月，11 表示12月
//        c.set(Calendar.DAY_OF_MONTH, 0);
//        c.set(Calendar.HOUR_OF_DAY, 0);
//        c.set(Calendar.MINUTE, 0);
//        c.set(Calendar.SECOND, 0);

                // 获取2012-12-21 0:0:0时间点对应的毫秒数
                long endTime = c.getTimeInMillis();
                // 获取系统当前时间
                Date now = new Date();
                // 获取当前时间点对应的毫秒数
                long currentTime = now.getTime();

                // 计算两个时间点相差的秒数
                long seconds = (endTime - currentTime) / 1000;

//        while (true) {
                long days = seconds / (3600 * 24);
                long h = seconds % (3600 * 24) / 3600;
                long m = seconds % (3600 * 24) % 3600 / 60;
                long s = seconds % (3600 * 24) % 3600 % 60;
                System.out.println("离2024年元旦还剩： " + days + "天" + h + "小时" + m + "分" + s + "秒");
                stringBuffer.append("离国庆节还剩： " + days + "天" + h + "小时" + m + "分" + s + "秒" + "\n");
                stringBuffer.append("链接:");
                stringBuffer.append("\n");
                stringBuffer.append("提取码:");

                textMessage = new TextMessage(requestMap, stringBuffer.toString());

            }
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + stringBuffer.toString());
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + "".equals(stringBuffer.toString()));
            if ("".equals(stringBuffer.toString())) {

                //2023、5、21修改获取资源策略，因为不加这个策略每次要添加两个不同网盘资源显示给用户都需要修改代码，
                //干脆改成获取目录名，目录名属于大名，
                // 好比：文件名为 洛雪音乐播放器蓝奏云，洛雪音乐播放器123盘  但是目录名为洛雪音乐播放器
                // 此时获取2023、5、21之后添加得数据就需要注意  目录名为一个，文件名需要修改网盘资源，单个为单个网盘资源，多个以此类推
                long count = iWxResourceService.count(queryWrapper.eq("directory_name", msg));
                if (count == 1) {
                    WxResource wxResourceMsg = iWxResourceService.getOne(queryWrapperDirectoryName.eq("directory_name", msg));
                    if (wxResourceMsg != null) {
                        textMessage = textMessageBdyWeChatRobot(wxResourceMsg, stringBuffer, textMessage, requestMap);
                        StringBuffer sb = new StringBuffer();
                        sb.append("\n");


                        WxResource wxResourceZygx = iWxResourceService.getOne(zygxQueryWrapper.eq("file_name", "夸克资源网盘"));
                        WxResource wxResourceZygxuc = iWxResourceService.getOne(uczygxQueryWrapper.eq("file_name", "迅雷资源网盘"));
                        if (wxResourceZygx != null) {
                            sb.append("\n");
                            sb.append("\n");
                            sb.append("\n");
                            sb.append("\n");
                            sb.append("\n☟☟☟☟☟\n");
                            sb.append(
                                    wxResourceZygx != null
                                            ? "整理合集一（每天更新）" +
                                            wxResourceZygx.getUrl()
                                            :
                                            "");
                            sb.append("\n");
                            if (wxResourceZygxuc != null) {
                                sb.append(
                                        wxResourceZygxuc != null
                                                ? "整理合集二（每天更新）" +
                                                wxResourceZygxuc.getUrl()
                                                :
                                                "");

                            }
                            sb.append("\n☝☝☝☝\n");
                        }
                        /**
                         WxResource wxResourceZygx = iWxResourceService.getOne(zygxQueryWrapper.eq("file_name", "zygx"));
                         sb.append("\n");
                         sb.append("\n");
                         sb.append("\n");

                         if (wxResourceZygx != null) {

                         sb.append("\n☟☟☟☟\n");
                         sb.append(
                         wxResource != null
                         ? "<a href=\"" +
                         wxResourceZygx.getUrl() +
                         "\">福利专区</a>"
                         :
                         "");
                         sb.append("\n☝☝☝☝\n");

                         sb.append("这是我这几年整理全网资源文档，需要的可以保存下载");
                         }
                         **/
                        textMessage.setContent(textMessage.getContent() + sb.toString());
                        return textMessage;
                    }
                }
                else if (count == 2) {
                    List<WxResource> wxResourceList = iWxResourceService.list(queryWrapperDirectoryName.eq("directory_name", msg));
                    StringBuffer sb = new StringBuffer();
                    int i = 0;
                    StringBuffer stringBuffer2 = new StringBuffer();
                    for (WxResource wxResource : wxResourceList) {
                        StringBuffer stringBuffer1 = new StringBuffer();
                        StringBuffer stringBufferF = new StringBuffer();
                        String content = i == 0 ? wxResource.getFileName().toString()  : "备用地址 " + wxResource.getFileName().toString();

                        stringBufferF.append(content + "\n");
                        stringBufferF.append("链接:");
                        stringBufferF.append(wxResource.getUrl() + "\n");
                        stringBufferF.append("提取码:");
                        stringBufferF.append(wxResource.getFetchCode() + "\n");
                        stringBufferF.append("\n");
                        stringBuffer2.append(stringBufferF);
                        i++;

                    }
                    for (WxResource wxResource : wxResourceList) {
                        if (!"".equals(wxResource.getArticleAddresses())){
                            stringBuffer2.append(!"".equals(wxResource.getArticleAddresses())  ? "使用教程" +
                                    wxResource.getArticleAddresses()
                                    :
                                    "");
                            break;
                        }
                    }


                    textMessage = new TextMessage(requestMap, stringBuffer2.toString());
                    sb.append("\n");



                    sb.append("\n");
                    sb.append("\n");
                    sb.append("\n");
                    sb.append("\n");
                    sb.append("\n☟☟☟☟☟\n");
                    WxResource wxResourceZygx = iWxResourceService.getOne(zygxQueryWrapper.eq("file_name", "夸克资源网盘"));
                    WxResource wxResourceZygxuc = iWxResourceService.getOne(uczygxQueryWrapper.eq("file_name", "迅雷资源网盘"));
                    if (wxResourceZygx != null) {
                        sb.append("\n");
                        sb.append("\n");
                        sb.append("\n");
                        sb.append("\n");
                        sb.append("\n☟☟☟☟☟\n");
                        sb.append(
                                wxResourceZygx != null
                                        ? "整理合集一（每天更新）" +
                                        wxResourceZygx.getUrl()
                                        :
                                        "");
                        sb.append("\n");
                        if (wxResourceZygxuc != null) {
                            sb.append(
                                    wxResourceZygxuc != null
                                            ? "整理合集二（每天更新）" +
                                            wxResourceZygxuc.getUrl()
                                            :
                                            "");
                        }
                        sb.append("\n☝☝☝☝\n");
                    }
                    /**
                     sb.append("\n");
                     sb.append("\n");
                     sb.append("\n");
                     WxResource wxResourceZygx = iWxResourceService.getOne(zygxQueryWrapper.eq("file_name", "zygx"));

                     if (wxResourceZygx != null) {

                     sb.append("\n☟☟☟☟☟\n");
                     sb.append(
                     wxResource != null
                     ? "<a href=\"" +
                     wxResourceZygx.getUrl() +
                     "\">福利专区</a>"
                     :
                     "");
                     sb.append("\n☝☝☝☝\n");

                     sb.append("这是我这几年整理全网资源文档，需要的可以保存下载");
                     }
                     **/
                    textMessage.setContent(textMessage.getContent() + sb.toString());
                    return textMessage;
                }
                else if (count == 3) {
                    List<WxResource> wxResourceList = iWxResourceService.list(queryWrapperDirectoryName.eq("directory_name", msg));
                    StringBuffer sb = new StringBuffer();
                    int i = 0;
                    StringBuffer stringBuffer2 = new StringBuffer();
                    for (WxResource wxResource : wxResourceList) {
                        StringBuffer stringBuffer1 = new StringBuffer();
                        StringBuffer stringBufferF = new StringBuffer();
                        String content = i == 0 ? wxResource.getFileName().toString()  : "备用地址 " + wxResource.getFileName().toString();

                        stringBufferF.append(content + "\n");
                        stringBufferF.append("链接:");
                        stringBufferF.append(wxResource.getUrl() + "\n");
                        stringBufferF.append("提取码:");
                        stringBufferF.append(wxResource.getFetchCode() + "\n");
                        stringBufferF.append("\n");
                        stringBuffer2.append(stringBufferF);
                        i++;

                    }
                    for (WxResource wxResource : wxResourceList) {
                        if (!"".equals(wxResource.getArticleAddresses())){
                            stringBuffer2.append(!"".equals(wxResource.getArticleAddresses())  ? "<a href=\"" +
                                    wxResource.getArticleAddresses() +
                                    "\">使用教程</a>"
                                    :
                                    "");
                            break;
                        }
                    }


                    textMessage = new TextMessage(requestMap, stringBuffer2.toString());
                    sb.append("\n");


                    WxResource wxResourceZygx = iWxResourceService.getOne(zygxQueryWrapper.eq("file_name", "夸克资源网盘"));
                    WxResource wxResourceZygxuc = iWxResourceService.getOne(uczygxQueryWrapper.eq("file_name", "迅雷资源网盘"));
                    if (wxResourceZygx != null) {
                        stringBuffer.append("\n");
                        stringBuffer.append("\n");
                        stringBuffer.append("\n");
                        stringBuffer.append("\n");
                        stringBuffer.append("\n☟☟☟☟☟\n");
                        stringBuffer.append(
                                wxResourceZygx != null
                                        ? "整理合集一（每天更新）" +
                                        wxResourceZygx.getUrl()
                                        :
                                        "");
                        stringBuffer.append("\n");
                        if (wxResourceZygxuc != null) {
                            stringBuffer.append(
                                    wxResourceZygxuc != null
                                            ? "整理合集二（每天更新）" +
                                            wxResourceZygxuc.getUrl()
                                            :
                                            "");
                        }
                        stringBuffer.append("\n☝☝☝☝\n");
                    }

                    /**

                     sb.append("\n");
                     sb.append("\n");
                     sb.append("\n");
                     WxResource wxResourceZygx = iWxResourceService.getOne(zygxQueryWrapper.eq("file_name", "zygx"));

                     if (wxResourceZygx != null) {


                     sb.append("\n☟☟☟☟☟\n");
                     sb.append(
                     wxResource != null
                     ? "<a href=\"" +
                     wxResourceZygx.getUrl() +
                     "\">福利专区</a>"
                     :
                     "");
                     sb.append("\n☝☝☝☝\n");

                     sb.append("这是我这几年整理全网资源文档，需要的可以保存下载");
                     }
                     **/
                    textMessage.setContent(textMessage.getContent() + sb.toString());
                    return textMessage;
                }
                else if (count == 4)  {
                    List<WxResource> wxResourceList = iWxResourceService.list(queryWrapperDirectoryName.eq("directory_name", msg));
                    StringBuffer sb = new StringBuffer();
                    int i = 0;
                    StringBuffer stringBuffer2 = new StringBuffer();
                    for (WxResource wxResource : wxResourceList) {
                        StringBuffer stringBuffer1 = new StringBuffer();
                        StringBuffer stringBufferF = new StringBuffer();
                        String content = i == 0 ? wxResource.getFileName().toString()  : "备用地址 " + wxResource.getFileName().toString();

                        stringBufferF.append(content + "\n");
                        stringBufferF.append("链接:");
                        stringBufferF.append(wxResource.getUrl() + "\n");
                        stringBufferF.append("提取码:");
                        stringBufferF.append(wxResource.getFetchCode() + "\n");
                        stringBufferF.append("\n");
                        stringBuffer2.append(stringBufferF);
                        i++;

                    }
                    for (WxResource wxResource : wxResourceList) {
                        if (!"".equals(wxResource.getArticleAddresses())){
                            stringBuffer2.append(!"".equals(wxResource.getArticleAddresses())  ? "<a href=\"" +
                                    wxResource.getArticleAddresses() +
                                    "\">使用教程</a>"
                                    :
                                    "");
                            break;
                        }
                    }


                    textMessage = new TextMessage(requestMap, stringBuffer2.toString());
                    sb.append("\n");


                    WxResource wxResourceZygx = iWxResourceService.getOne(zygxQueryWrapper.eq("file_name", "夸克资源网盘"));
                    WxResource wxResourceZygxuc = iWxResourceService.getOne(uczygxQueryWrapper.eq("file_name", "迅雷资源网盘"));
                    if (wxResourceZygx != null) {
                        stringBuffer.append("\n");
                        stringBuffer.append("\n");
                        stringBuffer.append("\n");
                        stringBuffer.append("\n");
                        stringBuffer.append("\n☟☟☟☟☟\n");
                        stringBuffer.append(
                                wxResourceZygx != null
                                        ? "整理合集一（每天更新）" +
                                        wxResourceZygx.getUrl()
                                        :
                                        "");
                        stringBuffer.append("\n");
                        if (wxResourceZygxuc != null) {
                            stringBuffer.append(
                                    wxResourceZygxuc != null
                                            ? "整理合集二（每天更新）" +
                                            wxResourceZygxuc.getUrl()
                                            :
                                            "");
                        }
                        stringBuffer.append("\n☝☝☝☝\n");
                    }
                    /**
                     sb.append("\n");
                     sb.append("\n");
                     sb.append("\n");
                     WxResource wxResourceZygx = iWxResourceService.getOne(zygxQueryWrapper.eq("file_name", "zygx"));

                     if (wxResourceZygx != null) {


                     sb.append("\n☟☟☟☟☟\n");
                     sb.append(
                     wxResource != null
                     ? "<a href=\"" +
                     wxResourceZygx.getUrl() +
                     "\">福利专区</a>"
                     :
                     "");
                     sb.append("\n☝☝☝☝\n");

                     sb.append("这是我这几年整理全网资源文档，需要的可以保存下载");
                     }
                     **/
                    textMessage.setContent(textMessage.getContent() + sb.toString());
                    return textMessage;
                }
                else {
                    int counter = 0; // 添加计数器


                    QueryWrapper<WxResource> queryLikeWrapper = new QueryWrapper<>();
                    List<WxResource> wxResourceList = iWxResourceService.list(queryLikeWrapper.like("directory_name", msg));// column_name 替换为你的数据库字段名
                    System.out.println("返回回复文本消息===============" + wxResourceList.size());

                    Set<String> uniqueDirectoryNames = new HashSet<>();

                    for (WxResource wxResource : wxResourceList) {
                        uniqueDirectoryNames.add(wxResource.getDirectoryName());
                    }

                    // 转换为列表以便输出和后续处理
                    List<String> uniqueDirectoryNameList = new ArrayList<>(uniqueDirectoryNames);

                    if (!uniqueDirectoryNameList.isEmpty()) {
                        StringBuffer sb = new StringBuffer();
                        StringBuffer stringBuffer2 = new StringBuffer();
                        StringBuffer stringBufferF = new StringBuffer();
                        stringBufferF.append("已匹配到相关资源：\n");
                        for (String directoryName : uniqueDirectoryNameList) {
                            stringBufferF.append(directoryName + "\n");
                        }
                        stringBuffer2.append(stringBufferF);
                        stringBuffer2.append("\n可回复具体资源获取");
                        System.out.println("返回回复文本消息" + stringBuffer2);
                        logger.info("返回回复文本消息" + stringBuffer2);
                        return new TextMessage(requestMap, stringBuffer2.toString());
                    }  else {
                        counter++; // 增加计数器
                        System.out.println("counter++" + counter++);

                        if (counter % 5 == 0) { // 如果计数器达到5的倍数，执行else块中的代码
                            WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "资源大全"));

                            stringBuffer.append("找不到该资源，关键字输入未匹配到或还未添加该资源");

                            WxResource wxResourceZygx = iWxResourceService.getOne(zygxQueryWrapper.eq("file_name", "夸克资源网盘"));
                            WxResource wxResourceZygxuc = iWxResourceService.getOne(uczygxQueryWrapper.eq("file_name", "迅雷资源网盘"));


                            if (wxResourceZygx != null) {

                                stringBuffer.append("\n");
                                stringBuffer.append("\n☟☟☟☟\n");
                                stringBuffer.append(
                                        wxResourceZygx != null
                                                ? "整理合集一（每天更新）" +
                                                wxResourceZygx.getUrl()
                                                :
                                                "");
                                stringBuffer.append("\n");
                                if (wxResourceZygxuc != null) {
                                    stringBuffer.append(
                                            wxResourceZygxuc != null
                                                    ? "整理合集二（每天更新）" +
                                                    wxResourceZygxuc.getUrl()
                                                    :
                                                    "");
                                }
                                stringBuffer.append("\n☝☝☝☝\n");
                            }
                            /**
                             stringBuffer.append("\n");
                             stringBuffer.append("\n");
                             stringBuffer.append("\n");
                             WxResource wxResourceZygx = iWxResourceService.getOne(zygxQueryWrapper.eq("file_name", "zygx"));

                             if (wxResourceZygx != null) {

                             stringBuffer.append("\n☟☟☟☟☟\n");
                             stringBuffer.append(
                             wxResource != null
                             ? "<a href=\"" +
                             wxResourceZygx.getUrl() +
                             "\">福利专区</a>"
                             :
                             "");
                             stringBuffer.append("\n☝☝☝☝\n");

                             stringBuffer.append("这是我这几年整理全网资源文档，需要的可以保存下载");
                             }
                             **/
                            return new TextMessage(requestMap, stringBuffer.toString());
                        }
                        return new TextMessage(requestMap, "找不到该资源");
                    }

                }
            }

        } else {
            QueryWrapper<WxResource> queryLikeWrapper = new QueryWrapper<>();
            List<WxResource> wxResourceList = iWxResourceService.list(queryLikeWrapper.like("directory_name", msg));// column_name 替换为你的数据库字段名

            if (!wxResourceList.isEmpty()) {
                // 查询结果为空
                StringBuffer sb = new StringBuffer();
                int i = 0;
                StringBuffer stringBuffer2 = new StringBuffer();
                StringBuffer stringBufferF = new StringBuffer();
                stringBufferF.append("已匹配到相关资源：\n");
                stringBufferF.append("☟☟☟☟");
                for (WxResource wxResource : wxResourceList) {
                    StringBuffer stringBuffer1 = new StringBuffer();
                    String content = wxResource.getDirectoryName().toString();

                    stringBufferF.append(content + "\n");
                    stringBuffer2.append(stringBufferF);
                    i++;

                }
                stringBufferF.append("☝☝☝☝");

                stringBuffer2.append("\n可回复以上具体资源获取");
                textMessage = new TextMessage(requestMap, stringBuffer2.toString());
                logger.info("返回回复文本消息" + textMessage);
            } else {
                WxResource wxResource = iWxResourceService.getOne(queryWrapper.eq("file_name", "资源大全"));

                stringBuffer.append("找不到该资源，关键字输入未匹配到或还未添加该资源");
                stringBuffer.append(",可参考：");

                stringBuffer.append(
                        wxResource != null
                                ? "<a href=\"" +
                                wxResource.getUrl() +
                                "\">资源目录</a>"
                                :
                                "<a href=\"" +
                                        "https://mp.weixin.qq.com/s?__biz=Mzk0MzMyMTI3Mg==&mid=2247485636&idx=1&sn=23bd2fbca8589eed8f1013c984982bcf&chksm=c334f113f443780524df240e82c9c4c08cbbe7af95b3f4afb9d236339bc9271d2b51e666e27b#rd" +
                                        "\">资源目录</a>");

                WxResource wxResourceZygx = iWxResourceService.getOne(zygxQueryWrapper.eq("file_name", "夸克资源网盘"));
                WxResource wxResourceZygxuc = iWxResourceService.getOne(uczygxQueryWrapper.eq("file_name", "迅雷资源网盘"));


                if (wxResourceZygx != null) {

                    stringBuffer.append("\n");
                    stringBuffer.append("\n");
                    stringBuffer.append("\n");
                    stringBuffer.append("\n");
                    stringBuffer.append("\n☟☟☟☟☟\n");
                    stringBuffer.append(
                            wxResourceZygx != null
                                    ? "整理合集一（每天更新）" +
                                    wxResourceZygx.getUrl()
                                    :
                                    "");
                    stringBuffer.append("\n");
                    if (wxResourceZygxuc != null) {


                        stringBuffer.append(
                                wxResourceZygxuc != null
                                        ? "整理合集二（每天更新）" +
                                        wxResourceZygxuc.getUrl()
                                        :
                                        "");
                    }
                    stringBuffer.append("\n☝☝☝☝\n");
                }


                textMessage = new TextMessage(requestMap, stringBuffer.toString());
                logger.info("返回回复文本消息" + textMessage);
            }



        }

        StringBuffer sb = new StringBuffer();
        sb.append("\n");

        WxResource wxResourceZygx = iWxResourceService.getOne(zygxQueryWrapper.eq("file_name", "zygx"));
        textMessage.setContent(textMessage.getContent() + sb.toString());
        return textMessage;
    }

    public String getVideoInfo() {
        com.alibaba.fastjson.JSONObject basicInfo = null;
        try {
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            // 注意时区，否则容易出错
            StringBuilder payload = new StringBuilder();
            payload.append("{\"type\":");
            payload.append("\"");

            payload.append("image");
            payload.append("\"");
            payload.append(",\"offset\":1");
            payload.append(",\"count\":20");
            payload.append("}");

            System.out.println("payload======>" + payload.toString());


            //发送请求的URL
            StringBuffer url = new StringBuffer();
            url.append("https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=");
            url.append("53_hcIKt5vg2MEGh6TYwLgCxMQ9atbUetC2So4yzKOzBNQC5kHZSIQ4NhOnNlJnLBeTvW-xklXGms86sAYc5errfUa_7Z3R5JDaBjEERV_N3ORV_0OOM_G2FZ0mlUTA5dH2XGfXxVbyUvEH1zXIQWBdAAAWCH");
            //使用帮助类HttpClients创建CloseableHttpClient对象
            CloseableHttpClient client = HttpClients.createDefault();
            //代理
            //HttpHost proxy = new HttpHost("10.19.108.2",8080);
            //HttpHost proxy = new HttpHost("120.79.7.36",80);

            //HTTP请求类型创建HttpPost实例
            HttpPost post = new HttpPost(url.toString());

            //开启代理
            //RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
            //post.setConfig(config);
            CloseableHttpResponse response = null;
            //string是一个json字符串
            StringEntity params = new StringEntity(payload.toString(), "UTF-8");
            params.setContentType("application/json;charset=UTF-8");
            post.setEntity(params);
            try {
                //通过执行HttpPost请求获取CloseableHttpResponse实例 ,从此CloseableHttpResponse实例中获取状态码,错误信息,以及响应页面等等.
                response = client.execute(post);
                System.out.println("<=====================>==>" + response);

                HttpEntity entity = response.getEntity();
                System.out.println("<=====================>==>" + response);

                Header headers[] = response.getAllHeaders();
                int i = 0;

                while (i < headers.length) {
                    System.out.println("请求头部信息=====>" + headers[i].getName() + ":  " + headers[i].getValue());
                    i++;
                }
                String resData = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                System.out.println("<=====================>==>" + resData);

                com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(resData);

                EntityUtils.consume(entity);
            } catch (
                    UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return basicInfo.toString();
    }


    StringBuffer stringBufferOne(WxResource wxResource) {
        logger.info("软件资源：{}" + wxResource);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(wxResource.getFileName() + "\n");
        stringBuffer.append("链接:");
        stringBuffer.append(wxResource.getUrl() + "\n");
        stringBuffer.append("提取码:");
        stringBuffer.append(wxResource.getFetchCode());
        return stringBuffer;
    }

    /**
     * 单个蓝奏云资源
     *
     * @param wxResourceLzy
     * @param stringBuffer
     * @param textMessage
     * @param requestMap
     * @return
     */
    TextMessage textMessageLzy(WxResource wxResourceLzy, StringBuffer stringBuffer, TextMessage textMessage, Map<String, String> requestMap) {
        if (wxResourceLzy != null) {
            stringBuffer.append(wxResourceLzy.getFileName() + "\n");
            stringBuffer.append("链接:");
            stringBuffer.append(wxResourceLzy.getUrl() + "\n");
            stringBuffer.append("提取码:");
            stringBuffer.append(wxResourceLzy.getFetchCode() + "\n" + "\n");
            stringBuffer.append(!"".equals(wxResourceLzy.getArticleAddresses()) || !"".equals(wxResourceLzy.getArticleAddresses()) ? "使用教程" +
                    wxResourceLzy.getArticleAddresses()
                    :
                    "");
            textMessage = new TextMessage(requestMap, stringBuffer.toString());
        } else {
            textMessage = new TextMessage(requestMap, notResourceContent);
        }
        return textMessage;
    }

    public static void main(String[] args) {

    }

    /**
     * 单个百度云资源
     *
     * @param wxResourceBdy
     * @param stringBuffer
     * @param textMessage
     * @param requestMap
     * @return
     */
    TextMessage textMessageBdyWeChatRobot(WxResource wxResourceBdy, StringBuffer stringBuffer, TextMessage textMessage, Map<String, String> requestMap) {

        if (wxResourceBdy != null) {
            if(wxResourceBdy.getSystemVersion().equals("ios")){
                stringBuffer.append("口令 : " +wxResourceBdy.getUrl() + "\n");

                textMessage = new TextMessage(requestMap, stringBuffer.toString());
            }else {

                stringBuffer.append(wxResourceBdy.getFileName() + "\n");
                stringBuffer.append("链接:");
                stringBuffer.append(wxResourceBdy.getUrl() + "\n");
                stringBuffer.append("提取码:");
                stringBuffer.append(wxResourceBdy.getFetchCode() + "\n" + "\n");



                stringBuffer.append(!"".equals(wxResourceBdy.getArticleAddresses()) || !"".equals(wxResourceBdy.getArticleAddresses())? "使用教程" +
                        wxResourceBdy.getArticleAddresses()
                        :
                        "");
                textMessage = new TextMessage(requestMap, stringBuffer.toString());
            }
        } else {
            textMessage = new TextMessage(requestMap, notResourceContent);
        }
        return textMessage;
    }

    /**
     * 单个百度云资源
     *
     * @param wxResourceBdy
     * @param stringBuffer
     * @param textMessage
     * @param requestMap
     * @return
     */
    TextMessage textMessageBdy(WxResource wxResourceBdy, StringBuffer stringBuffer, TextMessage textMessage, Map<String, String> requestMap) {

        if (wxResourceBdy != null) {
            if(wxResourceBdy.getSystemVersion().equals("ios")){
                stringBuffer.append("口令 : " +wxResourceBdy.getUrl() + "\n");
                stringBuffer.append("如想获取资源地第一时间可回复：加群");

                textMessage = new TextMessage(requestMap, stringBuffer.toString());
            }else {

                stringBuffer.append(wxResourceBdy.getFileName() + "\n");
                stringBuffer.append("链接:");
                stringBuffer.append(wxResourceBdy.getUrl() + "\n");
                stringBuffer.append("提取码:");
                stringBuffer.append(wxResourceBdy.getFetchCode() + "\n" + "\n");



                stringBuffer.append(!"".equals(wxResourceBdy.getArticleAddresses()) || !"".equals(wxResourceBdy.getArticleAddresses()) ? "使用教程" +
                        wxResourceBdy.getArticleAddresses()
                        :
                        "");
                textMessage = new TextMessage(requestMap, stringBuffer.toString());
            }
        } else {
            textMessage = new TextMessage(requestMap, notResourceContent);
        }
        return textMessage;
    }


    /**
     * 百度云+蓝奏云
     *
     * @param wxResourceLzy
     * @param stringBuffer
     * @param textMessage
     * @param requestMap
     * @return
     */
    TextMessage textMessageTwo(WxResource wxResourceBdy, WxResource wxResourceLzy, StringBuffer stringBuffer, TextMessage textMessage, Map<String, String> requestMap) {
        if (wxResourceLzy != null && wxResourceBdy != null) {


            stringBuffer.append(wxResourceLzy.getFileName() + "\n");
            stringBuffer.append("链接:");
            stringBuffer.append(wxResourceLzy.getUrl() + "\n");
            stringBuffer.append("提取码:");
            stringBuffer.append(wxResourceLzy.getFetchCode() + "\n");
            stringBuffer.append("\n");
            stringBuffer.append("备用地址 " + wxResourceBdy.getFileName() + "\n");
            stringBuffer.append("链接:");
            stringBuffer.append(wxResourceBdy.getUrl() + "\n");
            stringBuffer.append("提取码:");
            stringBuffer.append(wxResourceBdy.getFetchCode() + "\n" + "\n");

            stringBuffer.append(!"".equals(wxResourceBdy.getArticleAddresses()) || !"".equals(wxResourceLzy.getArticleAddresses()) ?  "使用教程" +
                    wxResourceBdy.getArticleAddresses()
                    :
                    "");
            textMessage = new TextMessage(requestMap, stringBuffer.toString());
        } else {
            textMessage = new TextMessage(requestMap, notResourceContent);
        }
        return textMessage;
    }

    /**
     * 百度云+蓝奏云+夸克云
     *
     * @param wxResourceLzy
     * @param stringBuffer
     * @param textMessage
     * @param requestMap
     * @return
     */
    TextMessage textMessageThree(WxResource wxResourceBdy, WxResource wxResourceLzy, WxResource wxResourceKk, StringBuffer stringBuffer, TextMessage textMessage, Map<String, String> requestMap) {
        if (wxResourceLzy != null && wxResourceBdy != null) {
            stringBuffer.append(wxResourceLzy.getFileName() + "\n");
            stringBuffer.append("链接:");
            stringBuffer.append(wxResourceLzy.getUrl() + "\n");
            stringBuffer.append("提取码:");
            stringBuffer.append(wxResourceLzy.getFetchCode() + "\n");
            stringBuffer.append("\n");
            stringBuffer.append(wxResourceBdy.getFileName() + "\n");
            stringBuffer.append("链接:");
            stringBuffer.append(wxResourceBdy.getUrl() + "\n");
            stringBuffer.append("提取码:");
            stringBuffer.append(wxResourceBdy.getFetchCode() + "\n");
            stringBuffer.append("\n");
            stringBuffer.append(wxResourceKk.getFileName() + "\n");
            stringBuffer.append("链接:");
            stringBuffer.append(wxResourceKk.getUrl() + "\n");
            stringBuffer.append("提取码:");
            stringBuffer.append(wxResourceKk.getFetchCode() + "\n" + "\n");
            stringBuffer.append(!"".equals(wxResourceBdy.getArticleAddresses()) || !"".equals(wxResourceLzy.getArticleAddresses())  ? "使用教程" +
                    wxResourceBdy.getArticleAddresses()
                    :
                    "");
            textMessage = new TextMessage(requestMap, stringBuffer.toString());
        } else {
            textMessage = new TextMessage(requestMap, notResourceContent);
        }
        return textMessage;
    }

    /**
     * 返回内容
     *
     * @param wxResourceLzy
     * @param stringBuffer
     * @param textMessage
     * @param requestMap
     * @return
     */
    TextMessage textMessage(WxResource wxResourceLzy, StringBuffer stringBuffer, TextMessage textMessage, Map<String, String> requestMap) {

        if (wxResourceLzy != null) {
            stringBuffer.append(wxResourceLzy.getContent());
            textMessage = new TextMessage(requestMap, stringBuffer.toString());
        } else {
            textMessage = new TextMessage(requestMap, notResourceContent);
        }
        return textMessage;
    }
}