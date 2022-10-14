package com.rzk.service;


import com.rzk.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-09-11
 */
public interface UserService extends IService<User> {

    /**
     * 新用户插入
     *
     * @param user
     * @return
     */
    public Integer insertUserMessage(User user);

    /**
     * 得到符合条件的用户数
     *
     * @param user
     * @return
     */

    //public Integer getUserCount(User user);

    /**
     * 更新用户信息
     *
     * @param user
     */
    public void updateUserMessage(User user);

    /**
     * 返回符合条件的用户信息
     */
    public List<User> getUserMessageByOtherMessage(User user);

    /**
     * 用户信息查询
     * @param usersId
     * @return
     */
    public User queryUsersInfo(String usersId);

    /**
     *查询用户是否喜欢视频
     */
    public boolean isUserLikeVideo(String userId,String videoId);

}