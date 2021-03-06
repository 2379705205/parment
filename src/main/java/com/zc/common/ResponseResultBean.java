package com.zc.common;

/**
 * @author zc
 * @explain
 * @date 2020/4/9 16:40
 */
public class ResponseResultBean implements  Response{

    //操作是否成功
    boolean success = SUCCESS;

    //操作代码
    int code = SUCCESS_CODE;

    //提示信息
    String message;

    Object object;

    public ResponseResultBean(boolean success, int code, String message, Object object) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.object = object;
    }

    public ResponseResultBean() {
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String toString() {
        return "ResponseResultBean{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", object=" + object +
                '}';
    }
}
