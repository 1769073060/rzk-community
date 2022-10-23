package com.rzk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.rzk.mapper.dao.UserDao;
import com.rzk.mapper.dao.UserFansMapper;
import com.rzk.mapper.dao.UsersLikeVideosDao;
import com.rzk.pojo.*;
import com.rzk.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2022-09-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Autowired
    private UserDao userMapper;
    @Resource
    private UsersLikeVideosDao usersLikeVideosDao;
    @Resource
    private UserFansMapper userFansMapper;
    /**
     * 新用户插入
     *
     * @param user
     * @return
     */
    public Integer insertUserMessage(User user) {
        return userMapper.insertUserMessage(user);
    }

    /**
     * 得到符合条件的用户数
     *
     * @param user
     * @return
     */

//    public Integer getUserCount(User user) {
//        return userMapper.selectCount(user);
//    }

    /**
     * 更新用户信息
     *
     * @param user
     */
    public void updateUserMessage(User user) {
        userMapper.updateById(user);
    }

    /**
     * 返回符合条件的用户信息
     */
    public List<User> getUserMessageByOtherMessage(User user) {


        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_openid",user.getUserOpenid());
        return userMapper.selectList(queryWrapper);
    }


    @Transactional(propagation = Propagation.SUPPORTS )
    @Override
    public User queryUsersInfo(String usersId) {
        UsersExample usersExample = new UsersExample();
        UsersExample.Criteria criteria = usersExample.createCriteria();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_id",usersId));
        return user;
    }

    @Transactional(propagation = Propagation.SUPPORTS )
    @Override
    public boolean isUserLikeVideo(String userId, String videoId) {
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)){
            return false;
        }
        UsersLikeVideosExample usersLikeVideosExample = new UsersLikeVideosExample();
        usersLikeVideosExample.createCriteria().andUserIdEqualTo(userId).andVideoIdEqualTo(videoId);
        List<UsersLikeVideos> usersLikeVideosList=usersLikeVideosDao.selectByExample(usersLikeVideosExample);
        System.out.println(usersLikeVideosList);
        if (usersLikeVideosList!=null && usersLikeVideosList.size()>0){
            System.out.println(111111);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.SUPPORTS )
    @Override
    public boolean queryIfFollow(String userId, String fanId) {

        UserFansExample userFansExample = new UserFansExample();
        userFansExample.createCriteria().andFanIdEqualTo(fanId).andUserIdEqualTo(userId);
        List<UserFans> userFansList  = userFansMapper.selectByExample(userFansExample);
        if (userFansList!= null && !userFansList.isEmpty() && userFansList.size()>0 ){
            return true;
        }
        return false;
    }
}