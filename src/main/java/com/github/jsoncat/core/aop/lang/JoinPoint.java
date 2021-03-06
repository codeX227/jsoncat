package com.github.jsoncat.core.aop.lang;

/**
 * @Description
 * @Author Goodenough
 * @Date 2021/3/6 16:33
 */
public interface JoinPoint {

    /**
     * get point this
     */
    Object getAdviceBean();

    /**
     * get target object
     */
    Object getTarget();

    /**
     * get parameters for object array
     */
    Object[] getArgs();
}
