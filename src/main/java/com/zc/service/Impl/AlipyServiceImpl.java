package com.zc.service.Impl;

import cn.isuyu.easy.pay.spring.boot.autoconfigure.dto.*;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.properties.AlipayProperties;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.service.AlipayService;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.utils.JsonUtils;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.vos.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.zc.config.WebSocketService;
import com.zc.dao.VerificationOrderRepository;
import com.zc.dao.VerificationZfbRepository;
import com.zc.pojo.Order;
import com.zc.pojo.Zfb;
import com.zc.service.AplipyService;
import com.zc.untils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * @author zc
 * @explain
 * @date 2020/4/23 20:52
 * 支付实现
 */
@Service
public class AlipyServiceImpl implements AplipyService {
    private static final Logger log = LoggerFactory.getLogger(AlipayService.class);
    @Autowired
    private AlipayProperties alipayProperties;
    @Autowired
    private HttpServletRequest request;
    private static final String FAST_INSTANT_TRADE_PAY = "FAST_INSTANT_TRADE_PAY";
    /**
     * 支付成功返回的状态值
     */
    private static final String SUCCESS_PAY_STATUS = "TRADE_SUCCESS";
    @Autowired
    VerificationZfbRepository verificationZfbRepository;
    @Autowired
    VerificationOrderRepository verificationOrderRepository;

    /**
     * 当面付回调二维码
     * @param alipayQrcodeDTO
     * @return
     */
    public AlipayQrcodeVO qrcode(AlipayQrcodeDTO alipayQrcodeDTO,String phone) {
        AlipayClient alipayClient = this.getAlipayClient();
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizContent(JsonUtils.jsonFormat(alipayQrcodeDTO));
        request.setNotifyUrl(this.alipayProperties.getNotifyUrl());
        log.debug(JSON.toJSONString(request));
        AlipayTradePrecreateResponse alipayTradePrecreateResponse = null;
        try {
            alipayTradePrecreateResponse = (AlipayTradePrecreateResponse)alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        log.debug(JSON.toJSONString(alipayTradePrecreateResponse));
        JSONObject qrcodeResponse = JSON.parseObject(alipayTradePrecreateResponse.getBody());
        AlipayQrcodeVO qrcodeVO = (AlipayQrcodeVO)qrcodeResponse.getObject("alipay_trade_precreate_response", AlipayQrcodeVO.class);
        Order order = new Order();
        order.setCreateTime(new Date());
        order.setOrderPhone(phone);
        order.setId(alipayQrcodeDTO.getOutTradeNo());
        order.setOrderName(alipayQrcodeDTO.getSubject());
        order.setOrderMoney(alipayQrcodeDTO.getTotalAmount());
        order.setPaymentType(1);
        order.setOrder_status(0);
        order.setOrderNo("支付显示后获取商品id");
        order.setOrderMoney(alipayQrcodeDTO.getTotalAmount());
       verificationOrderRepository.save(order);
        return qrcodeVO;
    }

    /**
     *支付回调
     * @return
     * @throws AlipayApiException
     */
    public AlipayCallBackVO callBack()  {
        Map<String, String> map = JsonUtils.requestToMap(this.request);
        boolean flag = false;
       /* Zfb zfb = this.getZfbConfig();*///明天做这个
        try {
            /*flag = AlipaySignature.rsaCheckV1(map, zfb.getPublicKey(), zfb.getCharset(), zfb.getSignType());*/
            flag = AlipaySignature.rsaCheckV1(map, this.alipayProperties.getPublicKey(), this.alipayProperties.getCharset(), this.alipayProperties.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        String json = JSON.toJSONString(map);
        log.debug(json);
        if (!flag) {
            throw new RuntimeException("支付宝回拨签名检查失败->>请检查公钥及私钥");
        } else {
            AlipayCallBackVO aliPayCallBackVO = (AlipayCallBackVO)JSON.parseObject(json, AlipayCallBackVO.class);
            if (aliPayCallBackVO.getTrade_status().equals(SUCCESS_PAY_STATUS)) {//支付成功
                Optional<Order> optional = verificationOrderRepository.findById(aliPayCallBackVO.getOut_trade_no());
                Order order = optional.get();
                order.setOrder_status(1);
                order.setOrderMoney(aliPayCallBackVO.getInvoice_amount());
                order.setEndTime(new Date());
                verificationOrderRepository.save(order);
                WebSocketService.sendMessage(JSON.toJSONString(aliPayCallBackVO), aliPayCallBackVO.getOut_trade_no());
                return aliPayCallBackVO;
            }
        }
        return null;
    }

    /**
     * 关闭订单
     * @param alipayCloseOrderDTO
     * @return
     * @throws AlipayApiException
     */
    public AlipayCloseOrderVO closeOrder(AlipayCloseOrderDTO alipayCloseOrderDTO) {
        AlipayClient alipayClient = this.getAlipayClient();
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizContent(JsonUtils.jsonFormat(alipayCloseOrderDTO));
        log.debug(JSON.toJSONString(request));
        AlipayTradeCloseResponse tradeCloseResponse = null;
        try {
            tradeCloseResponse = (AlipayTradeCloseResponse)alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        log.debug(JSON.toJSONString(tradeCloseResponse));
        JSONObject qrcodeResponse = JSON.parseObject(tradeCloseResponse.getBody());
        AlipayCloseOrderVO alipayCloseOrderVO = (AlipayCloseOrderVO)qrcodeResponse.getObject("alipay_trade_close_response", AlipayCloseOrderVO.class);
        return alipayCloseOrderVO;
    }

    /**
     * pc支付
     * @param alipayPcPayDTO
     * @return
     * @throws AlipayApiException
     */
    public String pcPay(AlipayPcPayDTO alipayPcPayDTO) {
        AlipayClient alipayClient = this.getAlipayClient();
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(this.alipayProperties.getReturnUrl());
        alipayRequest.setNotifyUrl(this.alipayProperties.getNotifyUrl());
        alipayPcPayDTO.setProductCode(FAST_INSTANT_TRADE_PAY);
        alipayRequest.setBizContent(JsonUtils.jsonFormat(alipayPcPayDTO));
        log.debug(JSON.toJSONString(alipayRequest));
        String form = null;
        try {
            form = ((AlipayTradePagePayResponse)alipayClient.pageExecute(alipayRequest)).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        log.debug(form);
        return form;
    }

    /**
     * h5支付
     * @param aliPayH5PayDTO
     * @return
     * @throws AlipayApiException
     */
    public String h5pay(AliPayH5PayDTO aliPayH5PayDTO)  {
        AlipayClient alipayClient = this.getAlipayClient();
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setBizContent(JsonUtils.jsonFormat(aliPayH5PayDTO));
        log.debug(JSON.toJSONString(request));
        AlipayTradeWapPayResponse tradeWapPayResponse = null;
        try {
            tradeWapPayResponse = (AlipayTradeWapPayResponse)alipayClient.pageExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        log.debug(JSON.toJSONString(tradeWapPayResponse));
        return tradeWapPayResponse.getBody();
    }

    /**
     * 退款接口
     * @param alipayRefundDTO
     * @return
     * @throws AlipayApiException
     */
    public AlipayRefundVO refund(AlipayRefundDTO alipayRefundDTO)  {
        AlipayClient alipayClient = this.getAlipayClient();
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(JsonUtils.jsonFormat(alipayRefundDTO));
        log.debug(JSON.toJSONString(request));
        AlipayTradeRefundResponse response = null;
        try {
            response = (AlipayTradeRefundResponse)alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        log.debug(JSON.toJSONString(response));
        JSONObject refundResponse = JSON.parseObject(response.getBody());
        AlipayRefundVO refundVO = (AlipayRefundVO)refundResponse.getObject("alipay_trade_refund_response", AlipayRefundVO.class);
        return refundVO;
    }

    /**
     * 退款查询接口
     * @param alipayRefundQueryDTO
     * @return
     * @throws AlipayApiException
     */
    public AlipayRefundQueryVO refundQuery(AlipayRefundQueryDTO alipayRefundQueryDTO) {
        AlipayClient alipayClient = this.getAlipayClient();
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        if (alipayRefundQueryDTO.getOutRequestNo() == null || "".equals(alipayRefundQueryDTO.getOutRequestNo())) {
            alipayRefundQueryDTO.setOutRequestNo(alipayRefundQueryDTO.getOutTradeNo());
        }
        request.setBizContent(JsonUtils.jsonFormat(alipayRefundQueryDTO));
        log.debug(JSON.toJSONString(request));
        AlipayTradeFastpayRefundQueryResponse response = null;
        try {
            response = (AlipayTradeFastpayRefundQueryResponse)alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        log.debug(JSON.toJSONString(response));
        JSONObject refundResponse = JSON.parseObject(response.getBody());
        return (AlipayRefundQueryVO)refundResponse.getObject("alipay_trade_fastpay_refund_query_response", AlipayRefundQueryVO.class);
    }

    /**
     * 获取参数
     * @return
     */
    public AlipayClient getAlipayClient(){
       /* Optional<Zfb> optional = verificationZfbRepository.findById(TokenUtil.getTokenByUserId());
        Zfb zfb = optional.get();
        System.out.println("->>"+zfb+"->>"+optional.isPresent());*/
        return new DefaultAlipayClient(this.alipayProperties.getGatewayUrl(), this.alipayProperties.getAppId(), this.alipayProperties.getPrivateKey(), "JSON", this.alipayProperties.getCharset(), this.alipayProperties.getPublicKey(), this.alipayProperties.getSignType());
    }
    public Zfb getZfbConfig(){
        Optional<Zfb> optional = verificationZfbRepository.findById(TokenUtil.getTokenByUserId());
        System.out.println("->>"+optional.get()+"->>"+optional.isPresent());
        return  optional.get();
    }


}
