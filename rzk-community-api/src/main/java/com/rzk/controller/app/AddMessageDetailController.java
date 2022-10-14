package com.rzk.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzk.pojo.Message;
import com.rzk.pojo.MessageImages;
import com.rzk.pojo.User;
import com.rzk.service.MessageImagesService;
import com.rzk.service.MessageService;
import com.rzk.service.UserService;
import com.rzk.utils.status.BaseResponse;
import com.rzk.utils.status.CodeEnum;
import com.rzk.utils.status.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class AddMessageDetailController {
    @Autowired
    private MessageImagesService messageImagesService;
    @Autowired
    private MessageService messageDetailService;
    @Autowired
    private com.rzk.service.UserService userService;


    /**
    @Transactional
    @PostMapping("/addMessage/{userId}")
    public IsUpload addMessage(@PathVariable Integer userId, @RequestBody Message message) {

        return new IsUpload().isTrue(message, messageDetailService, messageImagesService, userService);
    }
     **/
    @Transactional
    @PostMapping("/addMessage/{userId}")
    public BaseResponse addMessage(@PathVariable Integer userId, @RequestBody Message message) {
        log.info("addMessage:userId{}"+userId);
        log.info("addMessage:message{}"+message);
        //检查用户权限

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",message.getUserId());
        User user = userService.getOne(queryWrapper);
        if (userId == null) {
            return ResponseData.out(CodeEnum.TRESPASS);
        }
        if (user.getUserAllow()!=-1) {
            return ResponseData.out(CodeEnum.DISABLE_PERMISSIONS);
        }


        List<String> resultImage = message.getResultImage();

        messageDetailService.save(message);

        for (int i = 0; i < resultImage.size(); i++) {
            MessageImages messageImages = new MessageImages();
            if (resultImage.size()>0){
                messageImages.setImageUrl(resultImage.get(i));
            }
            messageImages.setMessageId(message.getMessageId());
            messageImagesService.save(messageImages);
        }

        return ResponseData.success("添加成功");

    }



}
