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
 * @date 2020/4/8 16:36
 */
@Getter
@Setter
@ToString
@Entity(name = "menu")
public class Menu {
    @Id
    private String id;
    @Column(name = "menu_name")
    private String menuName;
    @Column(name = "p_id")
    private String pId;
    @Column(name = "menu_url")
    private String menuUrl;
    @Column(name = "menu_code")
    private String menuCode;
    @Column(name = "order_num")
    private int orderNum;
    @Column(name = "is_menu")
    private String isMenu;
    @Column(name = "menu_pic")
    private String menuPic;
}
