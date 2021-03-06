package com.github.jsoncat.core.aop.intercept;

import com.github.jsoncat.annotation.aop.After;
import com.github.jsoncat.annotation.aop.Before;
import com.github.jsoncat.annotation.aop.Pointcut;
import com.github.jsoncat.common.util.ReflectionUtil;
import com.github.jsoncat.core.aop.lang.JoinPoint;
import com.github.jsoncat.core.aop.lang.JoinPointImpl;
import com.github.jsoncat.core.aop.util.PatternMatchUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * @Description 拦截标记了 @Aspect 类的拦截器,一个 @Aspect 类对应一个 InternallyAspectInterceptor 对象
 * @Author Goodenough
 * @Date 2021/3/6 13:44
 */
public class InternallyAspectInterceptor extends Interceptor{

    // 标记了 @Aspect 的 bean
    private final Object adviceBean;
    // 存放 @Pointcut 的节点表达式
    private final HashSet<String> expressions = new HashSet<>();
    // 存放 @Before 标记的前置通知
    private final List<Method> beforeMethods = new ArrayList<>();
    // 存放 @After 标记的后置通知
    private final List<Method> afterMethods = new ArrayList<>();

    public InternallyAspectInterceptor(Object adviceBean) {
        this.adviceBean = adviceBean;
        init();
    }

    private void init() {
        for (Method method : adviceBean.getClass().getMethods()) {
            Pointcut pointcut = method.getAnnotation(Pointcut.class);
            if (!Objects.isNull(pointcut)) {
                expressions.add(pointcut.value());
            }
            Before before = method.getAnnotation(Before.class);
            if (!Objects.isNull(before)) {
                beforeMethods.add(method);
            }
            After after = method.getAnnotation(After.class);
            if (!Objects.isNull(after)) {
                afterMethods.add(method);
            }
        }
    }

    @Override
    public boolean supports(Object bean) {
        return expressions.stream().anyMatch(url -> PatternMatchUtils.simpleMatch(url, bean.getClass().getName())) && (!beforeMethods.isEmpty() || !afterMethods.isEmpty());
    }

    @Override
    public Object intercept(MethodInvocation methodInvocation) {
        JoinPoint joinPoint = new JoinPointImpl(adviceBean, methodInvocation.getTargetObject(), methodInvocation.getArgs());
        beforeMethods.forEach(method -> ReflectionUtil.executeTargetMethodNoResult(adviceBean, method, joinPoint));
        Object result = methodInvocation.proceed();
        afterMethods.forEach(method -> ReflectionUtil.executeTargetMethodNoResult(adviceBean, method, result, joinPoint));
        return result;
    }
}
