package com.github.jsoncat.annotation.aop;

import java.lang.annotation.*;

/**
 * @Description 指定优先级
 * @Author Goodenough
 * @Date 2021/3/2 16:11
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Order {

    int value() default -1;
}
