package com.github.jsoncat.annotation.springmvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 指明请求路径中的参数
 * @Author Goodenough
 * @Date 2021/3/2 16:51
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PathVariable {

    String value();
}
