package com.rzk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.rzk.mapper.dao.WxAppUserMapper;
import com.rzk.pojo.WXMessage;
import com.rzk.pojo.WxAppUser;
import com.rzk.service.WxAppUserService;
import com.rzk.utils.DateUtil;
import com.rzk.utils.RestTemplateUtil;
import com.rzk.utils.common.HttpClientUtil;
import com.rzk.utils.exception.Asserts;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 小熊
 * @date: 2021/6/15 13:38
 * @description:phone 17521111022
 */
@Service("wxAppUserService")
public class WxAppUserServiceImpl implements WxAppUserService {

    @Resource
    private WxAppUserMapper wxAppUserMapper;

    @Resource
    private WXMessage wxMessage;

    @Resource
    private RestTemplateUtil restTemplateUtil;

    @Override
    public WxAppUser getUserInfoByOpenId(String openId) {
        QueryWrapper<WxAppUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", openId);
        List<WxAppUser> userList = wxAppUserMapper.selectList(queryWrapper);
        if (!userList.isEmpty())
            return userList.get(0);
        return null;
    }

    @Override
    public int insert(WxAppUser wxUser) {
        return wxAppUserMapper.insert(wxUser);
    }

    @Override
    public int updateById(WxAppUser wxUser) {
        return wxAppUserMapper.updateById(wxUser);
    }

    @Override
    public WxAppUser singIn(String openId) {
        WxAppUser wxUser = getUserInfoByOpenId(openId);
        if (wxUser == null)
            Asserts.wxInfoFail("获取用户信息失败");
        Date signTime = wxUser.getEndSignInTime();//最后签到时间
        Date nowDate = new Date();
        Date startDate = DateUtil.getStartTime(nowDate);//今日开始时间
        Date endDate = DateUtil.getEndTime(nowDate);//今日结束时间
        if (signTime == null || signTime.before(startDate) || signTime.after(endDate)) {
            wxUser.setSignInSum(wxUser.getSignInSum() + 1);
            wxUser.setVideoNumber(wxUser.getVideoNumber() + 20);
            wxUser.setEndSignInTime(nowDate);
            wxAppUserMapper.updateById(wxUser);
        } else {
            Asserts.wxInfoFail("重复签到");
        }
        return wxUser;
    }

    @Override
    public String getOpenId(String code) {
        String openId = null;
        /**StringBuilder url = new StringBuilder("https://api.weixin.qq.com/sns/jscode2session?");
        url.append("appid=" + WxConfig.appId);
        url.append("&secret=" + WxConfig.secret);
        url.append("&js_code=" + code);
        url.append("&grant_type=authorization_code");
        String data = restTemplateUtil.getForObject(url.toString(), null, String.class);**/

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+wxMessage.getWxId()+"&secret="+wxMessage.getWxSecret()+"&js_code="+code+"&grant_type=authorization_code";
        Map<String,Object> map = new HashMap<>();
        String token = null;
        String data = HttpClientUtil.doGet(url);

        Asserts.urlInfoNotNull(data, "openid解析异常");
        JSONObject jsonObject = JSON.parseObject(data);
        System.out.println(jsonObject);
        if (jsonObject.get("openid") == null) {
            Asserts.wxInfoFail(jsonObject.get("errmsg").toString());
        } else {
            openId = jsonObject.get("openid").toString();
        }
        return openId;
    }
}
