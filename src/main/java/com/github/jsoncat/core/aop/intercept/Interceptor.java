package com.github.jsoncat.core.aop.intercept;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description 拦截器接口
 * @Author Goodenough
 * @Date 2021/3/6 13:20
 */
@Getter
@Setter
public abstract class Interceptor {

    private int order = -1;

    public boolean supports(Object bean) {
        return false;
    }

    public abstract Object intercept(MethodInvocation methodInvocation);
}
