package com.zc.controller;

import com.zc.annotation.UserLoginToken;
import com.zc.common.ResponseResultList;
import com.zc.service.MenuService;
import com.zc.service.UserService;
import com.zc.untils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zc
 * @explain
 * @date 2020/4/9 14:10
 * 菜单控制器
 */
@RestController
public class MenuController {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    /**
     * 获取左侧菜单
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/menu")
    @UserLoginToken(required = true)
    public ResponseResultList getMenu(HttpServletRequest request, HttpServletResponse response){
        String userId = TokenUtil.getTokenByUserId();
        ResponseResultList resultList = menuService.findByUserId(userId);
        return resultList;
    }




}
