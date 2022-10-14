package com.rzk.config.isDelete;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzk.pojo.*;
import com.rzk.service.*;

import java.util.List;

public class IsDelete {


    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 500 服务器错误
     * 200 上传成功
     * 403 不允许发布,拉黑
     * 400 数据出现问题
     * 401  未登录
     * 1000 非法入侵
     */
    public IsDelete isDelete(Integer userId, Integer messageId, MessageImagesService messageImagesService, UserService UserService, MessageService messageDetailService, AttendService attendService, CollectService collectService,NewMessageService newMessageService) {
        IsDelete isDelete = new IsDelete();
        isDelete.setCode(500);

        User user = UserService.getById(userId);

        if (user == null) {
            isDelete.setCode(1000);
            return isDelete;
        }

        Message message = messageDetailService.getById(messageId);

        if (user.getUserIsAdmin() == 2 || message.getUserId() == user.getUserId()) {

            /**
             * 删除对应评论
             */
            messageDetailService.deleteCommentAndReply(messageId);
            /**
             * 删除我的参与
             */
            Attend attend = new Attend();
            attend.setMessageId(messageId);
            attendService.removeById(attend);
            /**
             * 删除收藏
             */
            Collect collect = new Collect();
            collect.setMessageId(messageId);
            collectService.removeById(collect);

            /**
             * 删除消息
             */
            NewMessage newMessage = new NewMessage();
            newMessage.setMessageId(messageId);
            newMessageService.removeById(newMessage);

            messageDetailService.removeById(messageId);
            MessageImages messageImages = new MessageImages();
            messageImages.setMessageId(messageId);

            QueryWrapper<MessageImages> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("message_id",messageImages.getMessageId());

            List<MessageImages> images = messageImagesService.list(queryWrapper);
            messageImagesService.removeById(messageImages);

            // Endpoint以杭州为例，其它Region请按实际情况填写。
            String endpoint = "你的阿里云实际位置地址";
            // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
            String accessKeyId = "你的阿里云id";
            String accessKeySecret = "你的阿里云密钥";
            String bucketName = "oss名称";


            // 创建OSSClient实例。
            /**
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            DeleteAliyunFile deleteAliyunFile = new DeleteAliyunFile();

            for (int i = 0; i < images.size(); i++) {
                String objectName = images.get(i).getImageUrl();
                deleteAliyunFile.DeleteAliyunFile(objectName, ossClient, bucketName);
            }
**/
            // 关闭OSSClient。
//            ossClient.shutdown();

            isDelete.setCode(200);
        }
        return isDelete;
    }
}
