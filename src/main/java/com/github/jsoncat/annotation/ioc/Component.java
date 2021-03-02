package com.github.jsoncat.annotation.ioc;

import java.lang.annotation.*;

/**
 * @Description 声明注入bean的注解
 * @Author Goodenough
 * @Date 2021/3/2 16:48
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    /**
     * bean默认名称
     */
    String name() default "";
}
