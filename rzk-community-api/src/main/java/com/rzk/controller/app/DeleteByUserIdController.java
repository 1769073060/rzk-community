package com.rzk.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzk.config.isDelete.IsDelete;
import com.rzk.pojo.*;
import com.rzk.service.*;
import com.rzk.utils.status.CodeEnum;
import com.rzk.utils.status.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class DeleteByUserIdController {
    @Autowired
    private MessageService messageDetailService;
    @Autowired
    private MessageImagesService messageImagesService;
    @Autowired
    private AttendService attendService;
    @Autowired
    private UserService userService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private NewMessageService newMessageService;


    @Transactional
    @PostMapping("/deleteMessageById/{userId}/{messageId}")
    public ResponseResult deleteByUserId(@PathVariable Integer userId, @PathVariable Integer messageId) {
        IsDelete isDelete = new IsDelete();
        isDelete.setCode(500);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        User user = userService.getOne(queryWrapper);

        if (user == null) {
            return new ResponseResult(CodeEnum.TRESPASS.getCode(), CodeEnum.TRESPASS.getMsg(), CodeEnum.TRESPASS.getMsg());

        }
        QueryWrapper<Message> messageQueryWrapper = new QueryWrapper<>();
        messageQueryWrapper.eq("message_id",messageId);
        Message message = messageDetailService.getOne(messageQueryWrapper);

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

            QueryWrapper<MessageImages> imagesQueryWrapper = new QueryWrapper<>();
            imagesQueryWrapper.eq("message_id", messageImages.getMessageId());

            List<MessageImages> images = messageImagesService.list(imagesQueryWrapper);
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
            return new ResponseResult(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg(), CodeEnum.SUCCESS.getMsg());
        }
        return new ResponseResult(CodeEnum.INTERNAL_SERVER_ERROR.getCode(), CodeEnum.INTERNAL_SERVER_ERROR.getMsg(), CodeEnum.INTERNAL_SERVER_ERROR.getMsg());

    }


}
