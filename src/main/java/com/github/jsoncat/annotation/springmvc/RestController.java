package com.github.jsoncat.annotation.springmvc;

import com.github.jsoncat.annotation.ioc.Component;

import java.lang.annotation.*;

/**
 * @Description 申明 Controller 的注解
 * @Author Goodenough
 * @Date 2021/3/2 16:51
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RestController {

    String value() default "";
}
