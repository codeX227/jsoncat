package com.github.jsoncat.core.springmvc.factory;

import com.github.jsoncat.annotation.springmvc.GetMapping;
import com.github.jsoncat.annotation.springmvc.PostMapping;
import com.github.jsoncat.annotation.springmvc.RestController;
import com.github.jsoncat.core.springmvc.entity.MethodDetail;
import com.github.jsoncat.factory.ClassFactory;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description 保存路由关系的映射信息
 * @Author Goodenough
 * @Date 2021/3/5 19:48
 */
public class RouteMethodMapper {

    public static final HttpMethod[] HTTP_METHODS = {HttpMethod.GET, HttpMethod.POST};

    //key : http method    value : formatted url -> method
    private static final Map<HttpMethod, Map<String, Method>> REQUEST_METHOD_MAP = new HashMap<>(2);

    // key : http method   value : formatted url -> original url
    private static final Map<HttpMethod, Map<String, String>> REQUEST_URL_MAP = new HashMap<>(2);

    static {
        for (HttpMethod httpMethod : HTTP_METHODS) {
            REQUEST_METHOD_MAP.put(httpMethod, new HashMap<>(128));
            REQUEST_URL_MAP.put(httpMethod, new HashMap<>(128));
        }
    }

    /**
     * 加载标记了 GetMapping 或 PostMapping 注解的方法并建立 url 映射
     */
    public static void loadRoutes(){
        Set<Class<?>> classes = ClassFactory.CLASS.get(RestController.class);
        for (Class<?> aClass : classes) {
            RestController restController = aClass.getAnnotation(RestController.class);
            if (restController != null) {
                String baseUrl = restController.value();
                Method[] methods = aClass.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(GetMapping.class)) {
                        GetMapping getMapping = method.getAnnotation(GetMapping.class);
                        if (getMapping != null) {
                            mapUrlToMethod(baseUrl+getMapping.value(), method, HttpMethod.GET);
                        }
                    }
                    if (method.isAnnotationPresent(PostMapping.class)) {
                        PostMapping postMapping = method.getAnnotation(PostMapping.class);
                        if (postMapping != null) {
                            mapUrlToMethod(baseUrl+postMapping.value(), method, HttpMethod.POST);
                        }
                    }
                }
            }
        }
    }

    /**
     * 建立 url 到 Method 的映射
     * @param url 映射的请求 url
     * @param method 请求对应映射的 Method
     * @param httpMethod HttpMethod
     */
    private static void mapUrlToMethod(String url, Method method, HttpMethod httpMethod){
        String formattedUrl = formatUrl(url);
        Map<String, Method> urlToMethodMap = REQUEST_METHOD_MAP.get(httpMethod);
        Map<String, String> formattedUrlToUrlMap = REQUEST_URL_MAP.get(httpMethod);
        if (urlToMethodMap.containsKey(formattedUrl)) {
            throw new IllegalArgumentException(String.format("duplicate url : %s", url));
        }
        urlToMethodMap.put(formattedUrl, method);
        formattedUrlToUrlMap.put(formattedUrl, url);
        REQUEST_METHOD_MAP.put(httpMethod, urlToMethodMap);
        REQUEST_URL_MAP.put(httpMethod, formattedUrlToUrlMap);
    }

    /**
     * 格式化 url
     */
    private static String formatUrl(String url) {
        // replace {xxx} placeholders with regular expressions matching Chinese, English letters and numbers, and underscores
        String originPattern = url.replaceAll("(\\{\\w+})", "[\\\\u4e00-\\\\u9fa5_a-zA-Z0-9]+");
        String pattern = "^" + originPattern + "/?$";
        return pattern.replaceAll("/+", "/");
    }

    /**
     * 构造 MethodDetail 对象
     * @param requestPath 请求路径
     * @param httpMethod 请求方式
     * @return MethodDetail
     */
    public static MethodDetail getMethodDetail(String requestPath, HttpMethod httpMethod) {
        MethodDetail methodDetail = new MethodDetail();
        methodDetail.build(requestPath, REQUEST_METHOD_MAP.get(httpMethod), REQUEST_URL_MAP.get(httpMethod));
        return methodDetail;
    }
}
