package com.zc.common;

import java.util.List;

/**
 * @author zc
 * @explain
 * @date 2020/4/9 14:38
 * 返回list
 */
public class ResponseResultList implements Response{
    //操作是否成功
    boolean success = SUCCESS;

    //操作代码
    int code = SUCCESS_CODE;

    //提示信息
    String message;

    List list;

    public ResponseResultList(boolean success) {
        this.success = success;
    }

    public ResponseResultList(boolean success, int code, String message, List list) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.list = list;
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

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
