package com.ftl1ght.yygh.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ftl1ght.yygh.common.exception.YyghException;
import com.ftl1ght.yygh.common.helper.JwtHelper;
import com.ftl1ght.yygh.common.result.ResultCodeEnum;
import com.ftl1ght.yygh.user.mapper.UserInfoMapper;
import com.ftl1ght.yygh.user.service.UserInfoService;
import com.yygh.model.user.UserInfo;

import com.yygh.vo.user.LoginVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author FTL1ght
 * @Description
 * @create 2023-01-01 20:47
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Map<String, Object> login(LoginVo loginVo) {

        String phone = loginVo.getPhone();
        String code = loginVo.getCode();

        //校验参数
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }

        //校验验证码
        String residCode = redisTemplate.opsForValue().get(phone);
        if (!code.equals(residCode)){
            throw new YyghException(ResultCodeEnum.CODE_ERROR);
        }

        //绑定手机号码
        UserInfo userInfo = null;
        if(!StringUtils.isEmpty(loginVo.getOpenid())) {
            userInfo = this.selectWxInfoOpenId(loginVo.getOpenid());
            if(null != userInfo) {
                userInfo.setPhone(loginVo.getPhone());
                this.updateById(userInfo);
            } else {
                throw new YyghException(ResultCodeEnum.DATA_ERROR);
            }
        }
        if (userInfo==null){
            //手机号已被使用
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone",phone);
            //获取会员
            userInfo = baseMapper.selectOne(queryWrapper);
            if(null == userInfo){
                userInfo = new UserInfo();
                userInfo.setName("");
                userInfo.setPhone(phone);
                userInfo.setStatus(1);
                this.save(userInfo);
            }
        }

        //校验是否被禁用
        if (userInfo.getStatus()==0) {
            throw new YyghException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }
        //TODO 记录登录

        //返回页面显示名称
        Map<String,Object> map = new HashMap<>();
        String name = userInfo.getName();
        if (StringUtils.isEmpty(name)){
            name = userInfo.getNickName();
        }
        if (StringUtils.isEmpty(name)) {
            name = userInfo.getPhone();
        }
        map.put("name",name);

        //jwt生成token字符串
        String token = JwtHelper.createToken(userInfo.getId(),name);

        map.put("token",token);

        return map;
    }

    @Override
    public UserInfo selectWxInfoOpenId(String openId) {

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openId",openId);
        UserInfo userInfo = baseMapper.selectOne(queryWrapper);

        return userInfo;
    }
}
