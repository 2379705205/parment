package com.zc.controller;

import com.zc.annotation.UserLoginToken;
import com.zc.pojo.User;
import com.zc.service.UserService;
import com.zc.untils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zc
 * @explain
 * @date 2020/4/9 22:36
 * 视图控制层
 */
@Controller

public class ViewController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/home")
    public String hello(Model model, HttpServletRequest request, HttpServletResponse response) {

        return "index";
    }
    @GetMapping(value = "/login")
    public String login(Model model) {
        return "login";
    }
    @GetMapping("view/user")
    public ModelAndView getViewUser(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("view/user");
        User user = userService.getIdByUser(TokenUtil.getTokenByUserId());
        mav.addObject("user",user);
        return mav;
    }
    @GetMapping(value = "/testcode")
    @UserLoginToken(required = true)
    public String test() {
        return "alipay/aliqrcode";
    }
    @GetMapping(value = "/pc")
    @UserLoginToken(required = true)
    public String test1() {
        return "alipay/alipcpay";
    }
    @GetMapping(value = "/alirefund")
    @UserLoginToken(required = true)
    public String test2() {
        return "alipay/alirefund";
    }


}
