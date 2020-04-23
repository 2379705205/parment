package com.zc.controller;

import cn.isuyu.easy.pay.spring.boot.autoconfigure.dto.*;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.vos.*;
import com.alipay.api.AlipayApiException;
import com.zc.annotation.UserLoginToken;
import com.zc.service.AplipyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zc
 * @explain
 * @date 2020/4/20 20:04
 * 支付接口
 */
@RestController
@RequestMapping(value = "alipay")
@Slf4j
@UserLoginToken(required = true)
public class AlipyController {
    @Autowired
    private AplipyService aplipyService;


    /**
     * 生成二维码
     *
     * @param qrcodeDTO
     * @return
     */
    @RequestMapping(value = "qrcode")
    public AlipayQrcodeVO qrcode(AlipayQrcodeDTO qrcodeDTO,String phone)  {
    log.info("生成二维码->>订单号"+qrcodeDTO.getOutTradeNo()+"订单金额->>"+qrcodeDTO.getTotalAmount()+"联系手机号->>"+phone);
        return aplipyService.qrcode(qrcodeDTO,phone);
    }

    /**
     * 支付宝pc端支付
     */
    @RequestMapping(value = "pcpay")
    public String pcPay(AlipayPcPayDTO pcPayDTO)  {
        return aplipyService.pcPay(pcPayDTO);
    }

    /**
     * h5支付
     *
     * @param aliPayH5PayDTO
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping(value = "h5pay")
    public String h5pay(AliPayH5PayDTO aliPayH5PayDTO)  {

        return aplipyService.h5pay(aliPayH5PayDTO);
    }


    /**
     * 订单关闭
     *
     * @param alipayCloseOrderDTO
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping(value = "close")
    public AlipayCloseOrderVO close(AlipayCloseOrderDTO alipayCloseOrderDTO) throws AlipayApiException {
       /* {"code":"10000","msg":"Success","out_trade_no":"1587454567778","trade_no":"2020042122001428891447793331","sub_code":null,"sub_msg":null}*/
        return aplipyService.closeOrder(alipayCloseOrderDTO);
    }

    /**
     * 支付回调 这里不管什么都全部返回SUCCESS
     * 当状态为WAIT_BUYER_PAY 也会对调一次
     * 如果不返回 支付宝会一直会回调
     *
     * @param request
     */
    @RequestMapping(value = "callback")
    public String payCallBack(HttpServletRequest request) throws AlipayApiException {
        AlipayCallBackVO aliPayCallBackVO = aplipyService.callBack();

        //log.info(aliPayCallBackVO.getOut_trade_no() + "-----" + aliPayCallBackVO.getTrade_status());
        System.out.println("回调函数->>"+aliPayCallBackVO);
        //支付成功通过websocket将回调结果返回给前端，
        // 我们生产环境需要判断是否回调结果状态并改变数据库中订单的值
        //返回给支付宝回调的接口已经封装好了，不管成功时候都是返回SUCCESS
        return aliPayCallBackVO.getShouldResonse();
    }

    /**
     * 支付宝退款
     */
    @RequestMapping(value = "refund")
    public AlipayRefundVO refund(AlipayRefundDTO refundDTO) throws AlipayApiException {
        return aplipyService.refund(refundDTO);
    }

    /**
     * 支付宝退款查询
     *
     * @param refundQueryDTO
     * @return
     */
    @RequestMapping(value = "refundQuery")
    public AlipayRefundQueryVO refundQuery(AlipayRefundQueryDTO refundQueryDTO) throws AlipayApiException {
        return aplipyService.refundQuery(refundQueryDTO);
    }
}
