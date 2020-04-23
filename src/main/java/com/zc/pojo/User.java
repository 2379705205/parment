package com.zc.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author zc
 * @explain
 * @date 2020/4/8 13:59
 */
@Getter
@Setter
@ToString
@Entity(name = "user")
public class User {
    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "updata_time")
    private Date updataTime;

}
