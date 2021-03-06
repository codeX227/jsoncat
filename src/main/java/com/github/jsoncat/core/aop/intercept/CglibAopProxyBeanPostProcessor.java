package com.github.jsoncat.core.aop.intercept;

import com.github.jsoncat.core.aop.proxy.CglibAspectProxy;

/**
 * @Description Cglib后置处理器
 * @Author Goodenough
 * @Date 2021/3/6 16:02
 */
public class CglibAopProxyBeanPostProcessor extends AbstractAopProxyBeanPostProcessor{

    @Override
    public Object wrapBean(Object target, Interceptor interceptor) {
        return CglibAspectProxy.wrap(target, interceptor);
    }
}
