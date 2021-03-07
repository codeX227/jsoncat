package com.github.jsoncat.core.springmvc.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jsoncat.annotation.springmvc.RequestBody;
import com.github.jsoncat.core.springmvc.entity.MethodDetail;

import java.lang.reflect.Parameter;

/**
 * @Description 处理 @RequestBody 标注的参数
 * @Author Goodenough
 * @Date 2021/3/7 16:21
 */
public class RequestBodyParameterResolver implements ParameterResolver{

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 处理方法的参数
     * @param methodDetail 目标方法的相关信息
     * @param parameter 要处理的目标方法的参数
     * @return 参数的值
     */
    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        Object param = null;
        RequestBody requestBody = parameter.getDeclaredAnnotation(RequestBody.class);
        if (requestBody != null) {
            try {
                param = OBJECT_MAPPER.readValue(methodDetail.getJson(), parameter.getType());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return param;
    }
}
