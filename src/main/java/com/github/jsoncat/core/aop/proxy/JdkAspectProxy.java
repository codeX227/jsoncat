package com.github.jsoncat.core.aop.proxy;

import com.github.jsoncat.core.aop.intercept.Interceptor;
import com.github.jsoncat.core.aop.intercept.MethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description Jdk 动态代理
 * @Author Goodenough
 * @Date 2021/3/6 15:45
 */
public class JdkAspectProxy implements InvocationHandler {

    private final Object target;
    private final Interceptor interceptor;

    public JdkAspectProxy(Object target, Interceptor interceptor){
        this.interceptor = interceptor;
        this.target = target;
    }

    public static Object wrap(Object target, Interceptor interceptor) {
        JdkAspectProxy jdkAspectProxy = new JdkAspectProxy(target, interceptor);
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                jdkAspectProxy
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        MethodInvocation methodInvocation = new MethodInvocation(target, method, args);
        return interceptor.intercept(methodInvocation);
    }
}