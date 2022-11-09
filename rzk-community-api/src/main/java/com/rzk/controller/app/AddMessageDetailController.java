package com.rzk.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzk.pojo.Message;
import com.rzk.pojo.MessageImages;
import com.rzk.pojo.User;
import com.rzk.service.MessageImagesService;
import com.rzk.service.MessageService;
import com.rzk.utils.BadWordUtils;
import com.rzk.utils.status.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
public class AddMessageDetailController {
    @Autowired
    private MessageImagesService messageImagesService;
    @Autowired
    private MessageService messageDetailService;
    @Autowired
    private com.rzk.service.UserService userService;
    @Autowired
    private BadWordUtils badWordUtils;

    /**
    @Transactional
    @PostMapping("/addMessage/{userId}")
    public IsUpload addMessage(@PathVariable Integer userId, @RequestBody Message message) {

        return new IsUpload().isTrue(message, messageDetailService, messageImagesService, userService);
    }
     **/
    @Transactional
    @PostMapping("/addMessage/{userId}")
    public ResponseResult addMessage(@PathVariable Integer userId, @RequestBody Message message) {
        log.info("addMessage:message{}"+userId);
        log.info("addMessage:message{}"+message);
        //检查用户权限

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",message.getUserId());
        User user = userService.getOne(queryWrapper);
        if (userId == null) {
            return new ResponseResult(CodeEnum.TRESPASS.getCode(), CodeEnum.TRESPASS.getMsg(), CodeEnum.TRESPASS.getMsg());
        }
        if (user.getUserAllow()!=-1) {
            return new ResponseResult(CodeEnum.DISABLE_PERMISSIONS.getCode(), CodeEnum.DISABLE_PERMISSIONS.getMsg(), CodeEnum.DISABLE_PERMISSIONS.getMsg());

        }


        List<String> resultImage = message.getResultImage();
        Set<String> sensitiveWordSet = null;
        try {
            sensitiveWordSet = badWordUtils.readResource();
        } catch (Exception e) {
            log.info("-----------------------初始化名词库失败--------------------------");
            e.printStackTrace();
        }
        //检查是否需要替换敏感词
        String filterStr = badWordUtils.replaceSensitive(message.getMessageDetail());
        message.setMessageDetail(filterStr);
        Integer messageId = messageDetailService.addMessage(message);

        if (messageId==1){
            //QueryWrapper<Message> queryWrapperMessageId = new QueryWrapper<>();
            //queryWrapperMessageId.eq("message_id",)
        }

        for (int i = 0; i < resultImage.size(); i++) {
            MessageImages messageImages = new MessageImages();
            if (resultImage.size()>0){
                messageImages.setImageUrl(resultImage.get(i));
            }
            messageImages.setMessageId(messageId);
            messageImagesService.save(messageImages);
        }
        return new ResponseResult(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg(), CodeEnum.SUCCESS.getMsg());

    }



}
