package com.zc.controller;

import com.zc.annotation.UserLoginToken;
import com.zc.common.ResponseResult;
import com.zc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zc
 * @explain
 * @date 2020/4/8 13:36
 */
@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult login(HttpServletRequest request, HttpServletResponse response) {
        Boolean isUser = userService.findByUser(request, response);
        if (!isUser) {
            return ResponseResult.FAIL();
        }
        return ResponseResult.SUCCESS();
    }
    @RequestMapping(value = "/updateByUserPassword",method = RequestMethod.POST)
    @ResponseBody
    @UserLoginToken(required = true)
    public ResponseResult updateByPassword(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = userService.updateByPassword(request,response);
        return result;
    }


}
