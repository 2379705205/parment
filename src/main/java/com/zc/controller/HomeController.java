package com.zc.controller;

import com.zc.annotation.UserLoginToken;
import com.zc.common.ResponseResultBean;
import com.zc.pojo.User;
import com.zc.service.UserService;
import com.zc.untils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zc
 * @explain
 * @date 2020/4/8 13:20
 */
@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @PostMapping("findByUser")
    @ResponseBody
    @UserLoginToken(required = true)
    public ResponseResultBean getUser(HttpServletRequest request, HttpServletResponse response) {
        String tokenByUserId = TokenUtil.getTokenByUserId();
        User user = userService.getIdByUser(tokenByUserId);
        if (StringUtils.isEmpty(user)) {
            return new ResponseResultBean(false, 100, "查询失败", user);
        }
        user.setId("");
        user.setUserName("");
        user.setUserPassword("");
        return new ResponseResultBean(true, 200, "查询成功", user);
    }


}
