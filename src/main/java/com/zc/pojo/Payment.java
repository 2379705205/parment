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
 * @date 2020/4/23 20:42
 * 支付实体
 */
@Getter
@Setter
@ToString
@Entity(name = "payment")
public class Payment {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "order_no")
    private String orderNo;
    @Column(name = "payment_type")
    private int paymentType;//wx 0 zfb 1
    @Column(name = "use_phone")
    private String usePhone;
    @Column(name = "order_money")
    private double orderMoney;
    @Column(name = "order_status")
    private int orderStatus;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "end_time")
    private Date endTime;

}
