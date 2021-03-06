package com.github.jsoncat.core.aop.factory;

import com.github.jsoncat.core.aop.intercept.BeanPostProcessor;
import com.github.jsoncat.core.aop.intercept.CglibAopProxyBeanPostProcessor;
import com.github.jsoncat.core.aop.intercept.JdkAopProxyBeanPostProcessor;

/**
 * @Description 生成后置处理器的工厂
 * @Author Goodenough
 * @Date 2021/3/6 15:48
 */
public class AopProxyBeanPostProcessorFactory {

    /**
     * @param beanClass 目标类
     * @return beanClass 实现了接口就返回jdk动态代理bean后置处理器,否则返回 cglib动态代理bean后置处理器
     */
    public static BeanPostProcessor get(Class<?> beanClass) {
        if (beanClass.isInterface() || beanClass.getInterfaces().length > 0)
            return new JdkAopProxyBeanPostProcessor();
        else
            return new CglibAopProxyBeanPostProcessor();
    }
}
