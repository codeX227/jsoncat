package com.github.jsoncat.core.springmvc.resolver;

import com.github.jsoncat.annotation.springmvc.PathVariable;
import com.github.jsoncat.common.util.ObjectUtil;
import com.github.jsoncat.core.springmvc.entity.MethodDetail;

import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * @Description 处理 @PathVariable 标注的参数
 * @Author Goodenough
 * @Date 2021/3/7 16:21
 */
public class PathVariableParameterResolver implements ParameterResolver{

    /**
     * 处理方法的参数
     * @param methodDetail 目标方法的相关信息
     * @param parameter 要处理的目标方法的参数
     * @return 参数的值
     */
    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        PathVariable pathVariable = parameter.getDeclaredAnnotation(PathVariable.class);
        String requestParameter = pathVariable.value();
        Map<String, String> urlParameterMappings = methodDetail.getUrlParameterMappings();
        String requestParameterValue = urlParameterMappings.get(requestParameter);
        return ObjectUtil.convert(parameter.getType(), requestParameterValue);
    }
}
