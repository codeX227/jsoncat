package com.github.jsoncat.annotation.springmvc;

import java.lang.annotation.*;

/**
 * @Description 处理 POST 请求
 * @Author Goodenough
 * @Date 2021/3/2 16:51
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PostMapping {

    String value() default "";
}
