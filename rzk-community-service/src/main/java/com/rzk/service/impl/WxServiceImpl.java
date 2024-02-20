package com.rzk.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rzk.pojo.consts.WxConsts;
import com.rzk.pojo.consts.WxResourcesConsts;
import com.rzk.pojo.wxserver.*;
import com.rzk.service.*;
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
    @Resource
    private IWxResourceService iWxResourceService;

    private final static String notResourceContent = "该资源不存在,或已失效,可联系我补上该资源!";


    /**
     * 用于处理所有的事件和消息的回复
     *
     * @param requestMap
     * @return
     */
    public String getResponse(Map<String, String> requestMap) {
        BaseMessage message = null;
        logger.info("消息数据{}:" + requestMap);

        //接收到的用户消息只需要获取到消息类型即可
        String msgType = requestMap.get("MsgType");
        logger.info("消息类型{}:" + msgType);

        //根据自己所需要的场景进行 回复相对于的消息内容
        switch (msgType) {
            // 文本消息
            case WxConsts.REQ_MESSAGE_TYPE_TEXT:
                logger.info("进入text");
                String msg = requestMap.get("Content");
                String substring = msg.substring(0, 2);

                //取出商品id，发送httpclient请求

                if (substring.equals("ht")) {
                    message = iTbTaoZheKouMessageService.replyTbZheKouMessage(requestMap);
                    break;
                } else {

                    QueryWrapper<WxUser> queryWxUser = new QueryWrapper<WxUser>();
                    WxUser from_user_name = iWxUserService.getOne(queryWxUser.eq("from_user_name", requestMap.get(WxConsts.FromUserName)));

                    if (from_user_name!=null){
                        logger.info("进入text");
                        /**if ("1".equals(from_user_name.getBlackFlag())){
                            message = iReplyMessageService.replyTextBlackFLagMessage(requestMap);
                        }else {}**/
                            message = iReplyMessageService.replyTextMessage(requestMap);

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

                    logger.info("message{}:" + message);

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
        if (message != null) {
            logger.info("消息======>{}:" + message);
            logger.info("消息======>{}:" + BeanToXml.objToXml(message));

            return BeanToXml.objToXml(message);
        }

        return null;
    }

    /**
     * 用于处理所有的事件和消息的回复的追加
     *
     * @param requestMap
     * @return
     */
    public String getResponses(Map<String, String> requestMap) {
        BaseMessage message = null;
        logger.info("RabbitMQ消息数据{}:" + requestMap);

        /**
         //接收到的用户消息只需要获取到消息类型即可
         String msgType = requestMap.get("MsgType");
         String fromUserName = requestMap.get("fromUserName").toString();
         String toUserName = requestMap.get("toUserName").toString();
         BaseMessage msg = new BaseMessage();
         msg.setToUserName(toUserName);
         msg.setFromUserName(fromUserName);
         System.out.println(requestMap.get("fromUserName").toString());
         //message.setFromUserName();
         //message.setToUserName(requestMap.get("toUserName").toString());


         Image image = new Image();
         image.setMediaId(WxResourcesConsts.Office_Media_Id_2010);
         message = new ImageMessage(requestMap, image);
         message.setToUserName(msg.getToUserName());
         message.setFromUserName(msg.getFromUserName());
         System.out.println("RabbitMQ:-------->"+message);
         if (message!=null){
         logger.info("消息======>{}:"+ BeanToXml.objToXml(message));
         BaseMessage finalMessage = message;

         return BeanToXml.objToXml(message);
         }**/

        return null;
    }

    @Override
    public BaseMessage replyTextMessage(Map<String, String> requestMap) {

        QueryWrapper<WxUser> queryWxUser = new QueryWrapper<WxUser>();
        WxUser from_user_name = iWxUserService.getOne(queryWxUser.eq("from_user_name", requestMap.get(WxConsts.FromUserName)));


        return iReplyMessageService.replyTextMessage(requestMap);


    }

    /**
     * 用于处理所有的事件和消息的回复
     *
     * @param requestMap
     * @return
     */
    public String getSyResponse(Map<String, String> requestMap) {
        BaseMessage message = null;
        logger.info("消息数据{}:" + requestMap);

        //接收到的用户消息只需要获取到消息类型即可
        String msgType = requestMap.get("MsgType");
        logger.info("消息类型{}:" + msgType);

        //根据自己所需要的场景进行 回复相对于的消息内容
        switch (msgType) {
            // 文本消息
            case WxConsts.REQ_MESSAGE_TYPE_TEXT:
                logger.info("进入text");
                String msg = requestMap.get("Content");
                String substring = msg.substring(0, 2);

                //取出商品id，发送httpclient请求

                if (substring.equals("ht")) {
                    message = iTbTaoZheKouMessageService.replyTbZheKouMessage(requestMap);
                    break;
                } else {

                    QueryWrapper<WxUser> queryWxUser = new QueryWrapper<WxUser>();
                    WxUser from_user_name = iWxUserService.getOne(queryWxUser.eq("from_user_name", requestMap.get(WxConsts.FromUserName)));

                    if (from_user_name!=null){
                        logger.info("进入text");
                        /**if ("1".equals(from_user_name.getBlackFlag())){
                            message = iReplyMessageService.replyTextBlackFLagMessage(requestMap);
                        }else {}**/
                            message = iReplyMessageService.replyTextMessage(requestMap);

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

                    logger.info("message{}:" + message);

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
        if (message != null) {
            logger.info("消息======>{}:" + message);
            logger.info("消息======>{}:" + BeanToXml.objToXml(message));

            return BeanToXml.objToXml(message);
        }

        return null;
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
    TextMessage textMessageLzy(WxResource wxResourceLzy, StringBuffer stringBuffer, TextMessage
            textMessage, Map<String, String> requestMap) {
        if (wxResourceLzy != null) {
            stringBuffer.append(wxResourceLzy.getFileName() + "\n");
            stringBuffer.append("链接:");
            stringBuffer.append(wxResourceLzy.getUrl() + "\n");
            stringBuffer.append("提取码:");
            stringBuffer.append(wxResourceLzy.getFetchCode() + "\n" + "\n");
            stringBuffer.append(!"".equals(wxResourceLzy.getArticleAddresses()) || !"".equals(wxResourceLzy.getArticleAddresses()) ? "<a href=\"" +
                    wxResourceLzy.getArticleAddresses() +
                    "\">使用教程</a>"
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
    TextMessage textMessageBdy(WxResource wxResourceBdy, StringBuffer stringBuffer, TextMessage
            textMessage, Map<String, String> requestMap) {

        if (wxResourceBdy != null) {
            if (wxResourceBdy.getSystemVersion().equals("ios")) {
                stringBuffer.append("口令 : " + wxResourceBdy.getUrl() + "\n");
                stringBuffer.append("如想获取资源地第一时间可回复：加群");

                textMessage = new TextMessage(requestMap, stringBuffer.toString());
            } else {

                stringBuffer.append(wxResourceBdy.getFileName() + "\n");
                stringBuffer.append("链接:");
                stringBuffer.append(wxResourceBdy.getUrl() + "\n");
                stringBuffer.append("提取码:");
                stringBuffer.append(wxResourceBdy.getFetchCode() + "\n" + "\n");


                stringBuffer.append(!"".equals(wxResourceBdy.getArticleAddresses()) || !"".equals(wxResourceBdy.getArticleAddresses()) ? "<a href=\"" +
                        wxResourceBdy.getArticleAddresses() +
                        "\">使用教程</a>"
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
    TextMessage textMessageTwo(WxResource wxResourceBdy, WxResource wxResourceLzy, StringBuffer
            stringBuffer, TextMessage textMessage, Map<String, String> requestMap) {
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

            stringBuffer.append(!"".equals(wxResourceBdy.getArticleAddresses()) || !"".equals(wxResourceLzy.getArticleAddresses()) ? "<a href=\"" +
                    wxResourceBdy.getArticleAddresses() +
                    "\">使用教程</a>"
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
    TextMessage textMessageThree(WxResource wxResourceBdy, WxResource wxResourceLzy, WxResource
            wxResourceKk, StringBuffer stringBuffer, TextMessage textMessage, Map<String, String> requestMap) {
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
            stringBuffer.append(!"".equals(wxResourceBdy.getArticleAddresses()) || !"".equals(wxResourceLzy.getArticleAddresses()) ? "<a href=\"" +
                    wxResourceBdy.getArticleAddresses() +
                    "\">使用教程</a>"
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
    TextMessage textMessage(WxResource wxResourceLzy, StringBuffer stringBuffer, TextMessage
            textMessage, Map<String, String> requestMap) {

        if (wxResourceLzy != null) {
            stringBuffer.append(wxResourceLzy.getContent());
            textMessage = new TextMessage(requestMap, stringBuffer.toString());
        } else {
            textMessage = new TextMessage(requestMap, notResourceContent);
        }
        return textMessage;
    }
}
