package com.ftl1ght.yygh.user.controller;

import com.ftl1ght.yygh.common.result.Result;
import com.ftl1ght.yygh.user.service.UserInfoService;
import com.yygh.vo.user.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author FTL1ght
 * @Description
 * @create 2023-01-01 20:46
 */
@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {

    @Autowired
    private UserInfoService userInfoService;

    //会员登录
    @PostMapping(value="login")
    public Result login(@RequestBody LoginVo loginVo,
                        HttpServletRequest request) {
        //loginVo.setIp(Iputil.getIpAddr(request));
        Map<String,Object> info = userInfoService.login(loginVo);
        return Result.ok(info);
    }

}


