package com.zc.service;

import com.zc.common.ResponseResult;
import com.zc.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zc
 * @explain
 * @date 2020/4/8 13:59
 */

public interface UserService {
     User getIdByUser(String userId);

    Boolean findByUser(HttpServletRequest request, HttpServletResponse response);

    ResponseResult updateByPassword(HttpServletRequest request, HttpServletResponse response);
}
