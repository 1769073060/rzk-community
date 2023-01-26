package com.rzk.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzk.pojo.consts.WxConsts;
import com.rzk.pojo.wxserver.BaseMessage;
import com.rzk.pojo.wxserver.TextMessage;
import com.rzk.pojo.wxserver.WxUser;
import com.rzk.service.IReplyMessageService;
import com.rzk.service.ITbTaoZheKouMessageService;
import com.rzk.service.IWxService;
import com.rzk.service.IWxUserService;
import com.rzk.utils.BeanToXml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @PackageName : com.rzk.service
 * @FileName : WxService
 * @Description : 用于处理所有的事件和消息的回复
 * @Author : rzk
 * @CreateTime : 19/1/2022 上午2:00
 * @Version : v1.0
 */
@DS("slave")
@Service
public class WxServiceImpl implements IWxService {
    private Logger logger = LoggerFactory.getLogger(WxServiceImpl.class);

    @Resource
    private IReplyMessageService iReplyMessageService;
    @Resource
    private ITbTaoZheKouMessageService iTbTaoZheKouMessageService;
    @Resource
    private IWxUserService iWxUserService;

    /**
     * 用于处理所有的事件和消息的回复
     * @param requestMap
     * @return
     */
    public String getResponse(Map<String, String> requestMap) {
        BaseMessage message = null;
        logger.info("消息数据{}:"+requestMap);

        //接收到的用户消息只需要获取到消息类型即可
        String msgType = requestMap.get("MsgType");
        logger.info("消息类型{}:"+msgType);

        //根据自己所需要的场景进行 回复相对于的消息内容
        switch (msgType){
            // 文本消息
            case WxConsts.REQ_MESSAGE_TYPE_TEXT:
                logger.info("进入text");
                String msg = requestMap.get("Content");
                String substring = msg.substring(0, 2);

                //取出商品id，发送httpclient请求

                if (substring.equals("ht")){
                    message = iTbTaoZheKouMessageService.replyTbZheKouMessage(requestMap);
                    break;
                }else {

                    QueryWrapper<WxUser> queryWxUser = new QueryWrapper<WxUser>();
                    WxUser from_user_name = iWxUserService.getOne(queryWxUser.eq("from_user_name", requestMap.get(WxConsts.FromUserName)));

                    if (from_user_name!=null){
                        logger.info("进入text");
                        if ("1".equals(from_user_name.getBlackFlag())){
                            message = iReplyMessageService.replyTextBlackFLagMessage(requestMap);
                        }else {
                            message = iReplyMessageService.replyTextMessage(requestMap);
                        }
                    }else {
                        //处理之前未做此功能关注的人
                        WxUser wxUser = new WxUser();
                        wxUser.setFromUserName(requestMap.get(WxConsts.FromUserName));
                        wxUser.setToUserName(requestMap.get(WxConsts.ToUserName));
                        wxUser.setFollowCount("1");
                        wxUser.setFollowDate(new Date());
                        wxUser.setBlackFlag("0");
                        iWxUserService.save(wxUser);
                        message = iReplyMessageService.replyTextMessage(requestMap);

                    }

                    break;
                }


            case WxConsts.REQ_MESSAGE_TYPE_IMAGE:

                break;
            case WxConsts.REQ_MESSAGE_TYPE_VOICE:

                break;
            case WxConsts.REQ_MESSAGE_TYPE_VIDEO:

                break;
            case WxConsts.REQ_MESSAGE_TYPE_SHORT_VIDEO:

                break;

            case WxConsts.REQ_MESSAGE_TYPE_LOCATION:

                break;
            case WxConsts.REQ_MESSAGE_TYPE_LINK:
                break;
            // 事件推送
            case WxConsts.REQ_MESSAGE_TYPE_MSG_TYPE:
                message = iReplyMessageService.replyEventMessage(requestMap);
                break;
            //            // 事件推送
//            else if (msgType.equals(WxConsts.REQ_MESSAGE_TYPE_EVENT)) {
//                // 事件类型
//                String eventType = (String) requestMap.get(WxConsts.Event);
//                // 关注
//                if (eventType.equals(WxConsts.EVENT_TYPE_SUBSCRIBE)) {
//                    respContent = "谢谢您的关注！";
//                    respXml = MsgUtil.sendTextMsg(requestMap, respContent);
//                }
//                // 取消关注
//                else if (eventType.equals(WxConsts.EVENT_TYPE_UNSUBSCRIBE)) {
//                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
//                }
//                // 扫描带参数二维码
//                else if (eventType.equals(WxConsts.EVENT_TYPE_SCAN)) {
//                    // TODO 处理扫描带参数二维码事件
//                }
//                // 上报地理位置
//                else if (eventType.equals(WxConsts.EVENT_TYPE_LOCATION)) {
//                    // TODO 处理上报地理位置事件
//                }
//                // 自定义菜单
//                else if (eventType.equals(WxConsts.EVENT_TYPE_CLICK)) {
//                    // TODO 处理菜单点击事件
//                }
//            }
            default:
                break;
        }
        if (message!=null){
            logger.info("消息======>{}:"+message);
            logger.info("消息======>{}:"+ BeanToXml.objToXml(message));
            return BeanToXml.objToXml(message);
        }

        return null;
    }


}