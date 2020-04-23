package com.zc.common;

import lombok.ToString;

/**
 * @author zc
 * @explain
 * @date 2020/4/8 16:09
 */
@ToString
public enum CommonCode implements ResultCode{
    SUCCESS(true,200,"操作成功!"),
    False(false,100,"操作失败!"),
    REPEAT(false,100,"密码与上次一致,修改失败"),
    Null(false,100,"请输入后在提交");
    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    CommonCode(boolean success,int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean success() {
        return success;
    }
    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
