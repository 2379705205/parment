package com.zc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zc
 * @explain
 * @date 2020/4/8 13:55
 * 有次注解可以直接通过
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JudgeToken {
    boolean required() default true;
}
