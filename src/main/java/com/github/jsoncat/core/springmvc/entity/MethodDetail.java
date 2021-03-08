package com.github.jsoncat.core.springmvc.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Description 方法与请求路径的映射
 * @Author Goodenough
 * @Date 2021/3/7 15:09
 */
@Getter
@Setter
@NoArgsConstructor
public class MethodDetail {

    // 需要映射的 Controller 方法
    private Method method;
    // url parameter mapping
    private Map<String, String> urlParameterMappings;
    // url query parameter mapping
    private Map<String, String> queryParameterMappings;
    // json type http post request data
    private String json;

    /**
     * 构造 MethodDetail 对象
     * @param requestPath 请求路径
     * @param requestMappings formatted url
     * @param urlMappings formatted url
     */
    public void build(String requestPath, Map<String, Method> requestMappings, Map<String, String> urlMappings) {
        requestMappings.forEach((key, value) -> {
            Pattern pattern = Pattern.compile(key);
            boolean b = pattern.matcher(requestPath).find();
            if (b) {
                this.setMethod(value);
                String url = urlMappings.get(key);
                Map<String, String> urlParameterMappings = getUrlParameterMappings(requestPath, url);
                this.setUrlParameterMappings(urlParameterMappings);
            }
        });
    }

    /**
     * 匹配请求路径参数和 URL 参数
     * 例如：requestPath="/user/1"  url="/user/{id}"
     *      return: {"id" -> "1", "user" -> "user"}
     * @param requestPath 请求路径参数
     * @param url original url
     * @return Map<String, String> urlParameterMappings
     */
    private Map<String, String> getUrlParameterMappings(String requestPath, String url) {
        String[] requestParams = requestPath.split("/");
        String[] urlParams = url.split("/");
        Map<String, String> urlParameterMappings = new HashMap<>();
        for (int i = 1; i < urlParams.length; i++) {
            urlParameterMappings.put(urlParams[i].replace("{", "").replace("}", ""), requestParams[i]);
        }
        return urlParameterMappings;
    }
}
