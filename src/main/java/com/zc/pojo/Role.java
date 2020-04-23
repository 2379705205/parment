package com.zc.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author zc
 * @explain
 * @date 2020/4/8 16:40
 */
@ToString
@Getter
@Setter
@Entity(name = "role")
public class Role {
    @Id
    private String id;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "update_user")
    private String updateUser;
    @Column(name = "role_level")
    private int roleLevel;
    @Column(name = "updat_time")
    private String updatTime;







}
