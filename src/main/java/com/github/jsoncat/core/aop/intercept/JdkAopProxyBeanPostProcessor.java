package com.github.jsoncat.core.aop.intercept;

import com.github.jsoncat.core.aop.proxy.JdkAspectProxy;

/**
 * @Description Jdk后置处理器
 * @Author Goodenough
 * @Date 2021/3/6 15:44
 */
public class JdkAopProxyBeanPostProcessor extends AbstractAopProxyBeanPostProcessor {

    @Override
    public Object wrapBean(Object target, Interceptor interceptor) {
        return JdkAspectProxy.wrap(target, interceptor);
    }
}
