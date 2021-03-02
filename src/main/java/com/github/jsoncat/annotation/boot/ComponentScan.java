package com.github.jsoncat.annotation.boot;

import java.lang.annotation.*;

/**
 * @Description 包扫描注解
 * @Author Goodenough
 * @Date 2021/3/2 16:43
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ComponentScan {

    String[] value() default {};
}
