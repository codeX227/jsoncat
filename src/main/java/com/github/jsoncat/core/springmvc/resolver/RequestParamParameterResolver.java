package com.github.jsoncat.core.springmvc.resolver;

import com.github.jsoncat.annotation.springmvc.RequestParam;
import com.github.jsoncat.common.util.ObjectUtil;
import com.github.jsoncat.core.springmvc.entity.MethodDetail;

import java.lang.reflect.Parameter;

/**
 * @Description 处理 @RequestParam 标注的参数
 * @Author Goodenough
 * @Date 2021/3/7 16:17
 */
public class RequestParamParameterResolver implements ParameterResolver {

    /**
     * 处理方法的参数
     * @param methodDetail 目标方法的相关信息
     * @param parameter 要处理的目标方法的参数
     * @return 参数的值
     */
    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        RequestParam requestParam = parameter.getDeclaredAnnotation(RequestParam.class);
        String requestParameter = requestParam.value();
        String requestParameterValue = methodDetail.getQueryParameterMappings().get(requestParameter);
        if (requestParameterValue == null) {
            throw new IllegalArgumentException("The specified parameter " + requestParameter + " can not be null!");
        }
        // convert the parameter to the specified type
        return ObjectUtil.convert(parameter.getType(), requestParameterValue);

    }
}
