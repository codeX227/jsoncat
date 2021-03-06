package com.github.jsoncat.core.aop.factory;

import com.github.jsoncat.annotation.aop.Aspect;
import com.github.jsoncat.annotation.aop.Order;
import com.github.jsoncat.common.util.ReflectionUtil;
import com.github.jsoncat.core.aop.intercept.BeanValidationInterceptor;
import com.github.jsoncat.core.aop.intercept.Interceptor;
import com.github.jsoncat.core.aop.intercept.InternallyAspectInterceptor;
import com.github.jsoncat.exception.CannotInitializeConstructorException;
import com.github.jsoncat.factory.ClassFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description 存放所有拦截器的工厂类
 * @Author Goodenough
 * @Date 2021/3/6 13:19
 */
public class InterceptorFactory {

    private static List<Interceptor> interceptors = new ArrayList<>();

    /**
     * 加载拦截器
     */
    public static void loadInterceptors(String[] packageNames){
        // 获取实现了 Interceptor 接口的类
        Set<Class<? extends Interceptor>> interceptorClasses = ReflectionUtil.getSubClass(packageNames, Interceptor.class);
        // 获取被 @Aspect 注解的类
        Set<Class<?>> aspects = ClassFactory.CLASS.get(Aspect.class);
        // 遍历拦截器类，存放到集合中
        interceptorClasses.forEach(interceptorClass -> {
            try {
                interceptors.add(interceptorClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new CannotInitializeConstructorException("not init construct, the interceptor name :"+interceptorClass.getSimpleName());
            }
        });
        // 遍历 @Aspect 注解的类，一个 @Aspect 类对应一个 InternallyAspectInterceptor 拦截器对象
        aspects.forEach(aClass -> {
            Object obj = ReflectionUtil.newInstance(aClass);
            Interceptor interceptor = new InternallyAspectInterceptor(obj);
            if (aClass.isAnnotationPresent(Order.class)) {
                Order order = aClass.getAnnotation(Order.class);
                interceptor.setOrder(order.value());
            }
            interceptors.add(interceptor);
        });
        // 添加 Bean 验证拦截器
        interceptors.add(new BeanValidationInterceptor());
        // 根据 Order 对拦截器排序
        interceptors = interceptors.stream().sorted(Comparator.comparing(Interceptor::getOrder)).collect(Collectors.toList());
    }

    public static List<Interceptor> getInterceptors() {
        return interceptors;
    }
}
