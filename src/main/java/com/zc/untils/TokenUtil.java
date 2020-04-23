package com.zc.untils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zc.pojo.User;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author zc
 * @explain
 * @date 2020/4/8 14:10
 * 请求头中获取token的工具类
 */
public class TokenUtil {
    /**
     * 获取token
     * @return
     */
    public static String getTokenByUserId(){
        String header = getRequest().getHeader("token");
        if (StringUtils.isEmpty(header)) {
            Cookie[] cookies = getRequest().getCookies();
            if (cookies!=null) {
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        header = cookie.getValue();
                    }
                }
            }
        }
        String token = JWT.decode(header).getAudience().get(0);
        return token;
    }
    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }
    public static String getToken(User user) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60* 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        String token = "";
        token = JWT.create().withAudience(user.getId()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getUserPassword()));
        return token;
    }

}
