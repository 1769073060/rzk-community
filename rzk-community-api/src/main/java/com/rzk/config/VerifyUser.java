package com.rzk.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzk.pojo.User;
import com.rzk.service.UserService;
import com.rzk.utils.status.MsgConsts;
import com.rzk.utils.status.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @PackageName : com.rzk.config
 * @FileName : VerifyUser
 * @Description : 校验用户
 * @Author : rzk
 * @CreateTime : 2022年 10月 02日 下午3:14
 * @Version : 1.0.0
 */
public class VerifyUser {



    public ResponseResult VerifyUserId(Integer userId,UserService userService){
        User user = userService.getOne(new QueryWrapper<User>().eq("user_id",userId));
        if (user==null){
            //用户为空
            return  new ResponseResult(MsgConsts.DATA_ERROR, null,null);
        }return new ResponseResult(MsgConsts.SUCCESS_CODE, null,null);

    }

    public ResponseResult VerifyUserIdAndUserAllow(Integer userId,UserService userService){
        User user = userService.getOne(new QueryWrapper<User>().eq("user_id",userId));
        if (user==null){
            //用户为空
            return  new ResponseResult(MsgConsts.DATA_ERROR, null,null);
        }
        if(user.getUserAllow()!=1){
            return  new ResponseResult(MsgConsts.DISALE_PERMISSIONS, null,null);
        }
        return new ResponseResult(MsgConsts.SUCCESS_CODE, null,null);
    }


}
