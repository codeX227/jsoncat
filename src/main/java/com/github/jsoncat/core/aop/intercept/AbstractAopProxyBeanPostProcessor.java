package com.github.jsoncat.core.aop.intercept;

import com.github.jsoncat.core.aop.factory.InterceptorFactory;

/**
 * @Description 后置处理器抽象类
 * @Author Goodenough
 * @Date 2021/3/6 15:45
 */
public abstract class AbstractAopProxyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean) {
        Object wrapperProxyBean = bean;
        // 链式包装目标类
        for (Interceptor interceptor : InterceptorFactory.getInterceptors()) {
            if (interceptor.supports(bean)) {
                wrapperProxyBean = wrapBean(wrapperProxyBean, interceptor);
            }
        }
        return wrapperProxyBean;
    }

    public abstract Object wrapBean(Object target, Interceptor interceptor);
}
