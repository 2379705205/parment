package com.zc.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.zc.common.CommonCode;
import com.zc.common.ResponseResult;
import com.zc.dao.VerificationUserRepository;
import com.zc.pojo.User;
import com.zc.service.UserService;
import com.zc.untils.GetRequestJsonUtils;
import com.zc.untils.TokenUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author zc
 * @explain
 * @date 2020/4/8 14:00
 */
@Service
public class UserServiceImpl implements UserService {
    Logger log = Logger.getLogger(UserServiceImpl.class);
    @Autowired
    private VerificationUserRepository verificationUserRepository;

    /**
     * 根据用户id查询用户
     *
     * @param userId
     * @return
     */
    public User getIdByUser(String userId) {
        Optional<User> optional = verificationUserRepository.findById(userId);
        if(!optional.isPresent()){
            return null;
        }
        log.info("根据id查询出的用户信息->>" + optional.get());
        return optional.get();
    }

    /**
     * 根据账号密码查询用户是否存在
     *
     * @param request
     * @param response
     * @return
     */
    public Boolean findByUser(HttpServletRequest request, HttpServletResponse response) {
        String jsonString = "";
        try {
             jsonString = GetRequestJsonUtils.getRequestJsonString(request);
        } catch (IOException e) {
            log.info("错误原因->>登录获取账号密码未知 转换json异常");
            return false;
        }
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String userName = jsonObject.get("loginUsername").toString();
        String password = jsonObject.get("loginPassword").toString();
        User user = verificationUserRepository.findByUserNameAndUserPassword(userName, password);
        log.info("登录者账号->>"+userName+"登陆者密码->>"+password+"登录结果->>"+user);
        if(StringUtils.isEmpty(user)){
            return false;
        }
        String token = TokenUtil.getToken(user);
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(10*60*60);
        cookie.setPath("/");
        response.addCookie(cookie);

        return true;
    }

    /**
     * 修改当前用户密码
     * @param request
     * @param response
     * @return
     */
    public ResponseResult updateByPassword(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result =null;
        /**
         * 0.先判断传入密码是否为空
         * 1.先查询当前用户是否存在
         * 2.查询当前用户的密码与修改密码是否一致
         * 3.进行修改
         * 4.删除当前用户的cookie or handler
         */
        String password = request.getParameter("userPassword");
        if (org.apache.commons.lang.StringUtils.isEmpty(password)) {
            result = new ResponseResult(CommonCode.Null);
            return result;
        }
        String id = TokenUtil.getTokenByUserId();
        Optional<User> optional = verificationUserRepository.findById(id);
        if(!optional.isPresent()){
            result = new ResponseResult(CommonCode.False);
            return result;
        }
        User user = optional.get();
        if (password.equalsIgnoreCase(user.getUserPassword())) {
            result = new ResponseResult(CommonCode.REPEAT);
            return result;
        }
        user.setUserPassword(password);
       verificationUserRepository.save(user);
        result = new ResponseResult(CommonCode.SUCCESS);
        return result;
    }
}
