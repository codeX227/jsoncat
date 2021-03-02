package com.github.jsoncat.annotation.springmvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 处理请求头参数的注解
 * @Author Goodenough
 * @Date 2021/3/2 16:52
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {

    String value();
}
