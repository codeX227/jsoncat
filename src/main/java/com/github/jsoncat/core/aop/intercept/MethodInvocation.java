package com.github.jsoncat.core.aop.intercept;

import com.github.jsoncat.common.util.ReflectionUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @Description 执行指定方法
 * @Author Goodenough
 * @Date 2021/3/6 13:35
 */
@Getter
@AllArgsConstructor
public class MethodInvocation {

    //target object
    private final Object targetObject;
    //target method
    private final Method targetMethod;
    //the parameter of target method
    private final Object[] args;

    /**
     * 执行指定方法
     * @return Object 方法执行结果
     */
    public Object proceed() {
        return ReflectionUtil.executeTargetMethod(targetObject, targetMethod, args);
    }
}
