package com.zc.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.websocket.Session;
/**
 * @author zc
 * @explain
 * @date 2020/4/20 22:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private static final long serialVersionUID = 8957107006902627635L;

    private String userName;

    private Session session;

}
