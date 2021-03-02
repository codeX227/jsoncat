package com.github.jsoncat.annotation.springmvc;

import java.lang.annotation.*;

/**
 * @Description 处理 GET 请求
 * @Author Goodenough
 * @Date 2021/3/2 16:51
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetMapping {

    String value() default "";
}
