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
 * @date 2020/4/23 21:55
 */
@Getter
@Setter
@ToString
@Entity(name = "t_order")
public class Order {
    @Id
    private String id;
    @Column(name = "order_name")
    private String orderName;
    @Column(name = "order_no")
    private String orderNo;
    @Column(name = "order_phone")
    private String orderPhone;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "payment_type")
    private int paymentType;
    @Column(name = "order_money")
    private double orderMoney;
    @Column(name = "orderStatus")
    private int order_status;


}
