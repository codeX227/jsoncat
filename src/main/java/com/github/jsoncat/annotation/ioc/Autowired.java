package com.github.jsoncat.annotation.ioc;

import java.lang.annotation.*;

/**
 * @Description 自动注入的注解
 * @Author Goodenough
 * @Date 2021/3/2 16:47
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

}
