package com.github.jsoncat.core.aop.intercept;

/**
 * @Description bean 后置处理器
 * @Author Goodenough
 * @Date 2021/3/6 15:46
 */
public interface BeanPostProcessor {

    default Object postProcessAfterInitialization(Object bean) {
        return bean;
    }
}
