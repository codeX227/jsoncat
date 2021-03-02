package com.github.jsoncat.annotation.config;

import java.lang.annotation.*;

/**
 * @Description 配置文件取值的注解
 * @Author Goodenough
 * @Date 2021/3/2 16:46
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {

    /**
     * example: "my.app.myProp"
     */
    String value();
}
