package com.zc.Interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zc.annotation.JudgeToken;
import com.zc.annotation.UserLoginToken;
import com.zc.pojo.User;
import com.zc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @author zc
 * @explain
 * @date 2020/4/8 13:58
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        if (StringUtils.isEmpty(token)) {
            //证明请求头中没有token,需要从cookie中获取
            Cookie[] cookies = request.getCookies();
            if (cookies !=null) {
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        token = cookie.getValue();
                    }
                }
            }
            if (!(handler instanceof HandlerMethod)) {
                return true;
            }
            HandlerMethod handlerMethod =  (HandlerMethod)handler;
            Method methodMethod = handlerMethod.getMethod();
            if (methodMethod.isAnnotationPresent(JudgeToken.class)) {
                return true;
            }
            if (methodMethod.isAnnotationPresent(UserLoginToken.class)) {
                UserLoginToken loginToken = methodMethod.getAnnotation(UserLoginToken.class);
                if (loginToken.required()) {
                    if (StringUtils.isEmpty(token)) {
                        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                            response.setHeader("executeStatus", "sessionTimeout");// 在响应头设置session状态
                            response.setHeader("url", new StringBuffer(request.getContextPath()).append("/login").toString());
                        }
                        response.setHeader("content-type", "text/html;charset=UTF-8");
                        PrintWriter out = response.getWriter();
                        StringBuilder builder = new StringBuilder();
                        builder.append("<script type=\"text/javascript\" charset=\"UTF-8\" src=\"").append(request.getContextPath()).append("js/jquery-2.1.1.min.js\">").append("</script>");
                        builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
                        builder.append("$(function(){window.top.location.href='").append(request.getContextPath()).append("/login';});");
                        builder.append("</script>");
                        out.print(builder.toString());
                        out.close();
                    }
                    String userId = "";
                    try {
                        userId = JWT.decode(token).getAudience().get(0);
                    }catch (JWTDecodeException j){
                        throw new RuntimeException("获取用户id出现错误 可能不存在此token");
                    }
                    User user = userService.getIdByUser(userId);
                    if (StringUtils.isEmpty(user)) {
                        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                            response.setHeader("executeStatus", "sessionTimeout");// 在响应头设置session状态
                            response.setHeader("url", new StringBuffer(request.getContextPath()).append("/login").toString());
                        }
                        response.setHeader("content-type", "text/html;charset=UTF-8");
                        PrintWriter out = response.getWriter();
                        StringBuilder builder = new StringBuilder();
                        builder.append("<script type=\"text/javascript\" charset=\"UTF-8\" src=\"").append(request.getContextPath()).append("js/jquery-2.1.1.min.js\">").append("</script>");
                        builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
                        builder.append("$(function(){window.top.location.href='").append(request.getContextPath()).append("/login';});");
                        builder.append("</script>");
                        out.print(builder.toString());
                        out.close();
                    }
                    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(user.getUserPassword())).build();
                    try {
                        verifier.verify(token);
                    } catch (JWTVerificationException e) {
                        throw new RuntimeException("验证错误 可能已经修改了密码");
                    }
                    return true;
                }
                }
            }
            return true;
    }
}
