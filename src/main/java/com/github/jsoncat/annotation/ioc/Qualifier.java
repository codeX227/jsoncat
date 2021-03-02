package com.github.jsoncat.annotation.ioc;

import java.lang.annotation.*;

/**
 * @Description 指定bean名称自动注入的注解
 * @Author Goodenough
 * @Date 2021/3/2 16:49
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Qualifier {

    String value() default "";
}
