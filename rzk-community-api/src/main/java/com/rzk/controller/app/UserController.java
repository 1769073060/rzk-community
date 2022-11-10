package com.rzk.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rzk.pojo.User;
import com.rzk.pojo.UserReport;
import com.rzk.service.UserReportService;
import com.rzk.service.UserService;
import com.rzk.utils.JSONResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @PackageName : com.rzk.controller.app
 * @FileName : UserController
 * @Description : 用户相关业务
 * @Author : rzk
 * @CreateTime : 2022年 11月 10日 上午10:42
 * @Version : 1.0.0
 */
@RestController
@Api(value = "用户有关业务的接口", tags = {"用户业务controller"})
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserReportService userReportService;
    @Autowired
    private UserService userService;


    @PostMapping("/reportUser")
    public JSONResult reportUser(@RequestBody UserReport userReport) {
        userReportService.save(userReport);
        return JSONResult.errorMsg("举报核对中");
    }


    @PostMapping("/editUser")
    public JSONResult editUser(@RequestBody User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_nickname",user.getUserNickname());

        List<User> userList = userService.list(userQueryWrapper);
        if (userList.size()>0){
            return JSONResult.errorMsg("用户名被占领了喔，请换一个试试");

        }


        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",user.getUserId());

        userService.update(user,updateWrapper);
        return JSONResult.ok("修改成功");
    }

}
