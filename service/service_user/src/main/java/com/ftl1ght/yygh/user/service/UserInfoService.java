package com.ftl1ght.yygh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yygh.model.user.UserInfo;
import com.yygh.vo.user.LoginVo;

import java.util.Map;

/**
 * @author FTL1ght
 * @Description
 * @create 2023-01-01 20:47
 */
public interface UserInfoService extends IService<UserInfo> {
    Map<String, Object> login(LoginVo loginVo);

    UserInfo selectWxInfoOpenId(String openId);
}
