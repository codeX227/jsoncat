package com.github.jsoncat.core.aop.proxy;

import com.github.jsoncat.core.aop.intercept.Interceptor;
import com.github.jsoncat.core.aop.intercept.MethodInvocation;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description Cglib 动态代理
 * @Author Goodenough
 * @Date 2021/3/6 15:43
 */
public class CglibAspectProxy implements MethodInterceptor {

    private final Object target;
    private final Interceptor interceptor;

    public CglibAspectProxy(Object target, Interceptor interceptor){
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object wrap(Object target, Interceptor interceptor) {
        Class<?> rootClass = target.getClass();
        Class<?> proxySuperclass = rootClass;
        // cglib多级代理
        if (target.getClass().getName().contains("$$")) {
            proxySuperclass = rootClass.getSuperclass();
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(target.getClass().getClassLoader());
        enhancer.setSuperclass(proxySuperclass);
        enhancer.setCallback(new CglibAspectProxy(target, interceptor));
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) {
        MethodInvocation methodInvocation = new MethodInvocation(target, method, args);
        return interceptor.intercept(methodInvocation);
    }
}
