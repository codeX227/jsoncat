package com.github.jsoncat.annotation.boot;

import java.lang.annotation.*;

/**
 * @Description 启动类注解
 * @Author Goodenough
 * @Date 2021/3/2 16:44
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ComponentScan
public @interface SpringBootApplication {

}
