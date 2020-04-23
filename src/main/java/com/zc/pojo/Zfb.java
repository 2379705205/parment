package com.zc.pojo;

import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author zc
 * @explain
 * @date 2020/4/23 21:00
 */
@ToString
@Entity(name = "zfb")
public class Zfb {
    @Id
    private String id;
    @Column(name = "app_id")
    private String appId;
    @Column(name = "private_key")
    private String privateKey;
    @Column(name = "public_key")
    private String publicKey;
    @Column(name = "notify_url")
    private String notifyUrl;
    @Column(name = "return_url")
    private String returnUrl;
    @Column(name = "sign_type")
    private String signType;
    @Column(name = "charset")
    private String charset;
    @Column(name = "gateway_url")
    private String gatewayUrl;
    @Column(name = "log_path")
    private String logPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getSignType() {
        return signType;
    }

    public String getCharset() {
        return charset;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }
}
