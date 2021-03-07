package com.github.jsoncat.core.springmvc.resolver;

import com.github.jsoncat.core.springmvc.entity.MethodDetail;

import java.lang.reflect.Parameter;

/**
 * @Description 参数解析器的接口
 * @Author Goodenough
 * @Date 2021/3/7 16:17
 */
public interface ParameterResolver {

    /**
     * 处理方法的参数
     * @param methodDetail 目标方法的相关信息
     * @param parameter 要处理的目标方法的参数
     * @return 参数的值
     */
    Object resolve(MethodDetail methodDetail, Parameter parameter);
}
