package com.github.jsoncat.core.springmvc.factory;

import com.github.jsoncat.annotation.springmvc.PathVariable;
import com.github.jsoncat.annotation.springmvc.RequestBody;
import com.github.jsoncat.annotation.springmvc.RequestParam;
import com.github.jsoncat.core.springmvc.resolver.ParameterResolver;
import com.github.jsoncat.core.springmvc.resolver.PathVariableParameterResolver;
import com.github.jsoncat.core.springmvc.resolver.RequestBodyParameterResolver;
import com.github.jsoncat.core.springmvc.resolver.RequestParamParameterResolver;

import java.lang.reflect.Parameter;

/**
 * @Description 参数解析器的工厂
 * @Author Goodenough
 * @Date 2021/3/7 16:16
 */
public class ParameterResolverFactory {

    /**
     * 根据参数标记的注解，创建对应参数解析器
     * @param parameter 要解析的参数
     * @return ParameterResolver 参数解析器
     */
    public static ParameterResolver get(Parameter parameter) {
        if (parameter.isAnnotationPresent(RequestParam.class)) {
            return new RequestParamParameterResolver();
        }
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            return new PathVariableParameterResolver();
        }
        if (parameter.isAnnotationPresent(RequestBody.class)) {
            return new RequestBodyParameterResolver();
        }
        return null;
    }
}
