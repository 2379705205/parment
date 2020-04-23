package com.zc.service;

import cn.isuyu.easy.pay.spring.boot.autoconfigure.dto.*;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.vos.*;

/**
 * @author zc
 * @explain
 * @date 2020/4/23 20:54
 */
public interface AplipyService {

    public AlipayQrcodeVO qrcode(AlipayQrcodeDTO alipayQrcodeDTO,String phone);

    public AlipayCallBackVO callBack();

    public AlipayCloseOrderVO closeOrder(AlipayCloseOrderDTO alipayCloseOrderDTO);

    public String pcPay(AlipayPcPayDTO alipayPcPayDTO);

    public String h5pay(AliPayH5PayDTO aliPayH5PayDTO);

    public AlipayRefundVO refund(AlipayRefundDTO alipayRefundDTO);

    public AlipayRefundQueryVO refundQuery(AlipayRefundQueryDTO alipayRefundQueryDTO);
}
