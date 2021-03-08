package com.github.jsoncat.core.springmvc.factory;

import com.github.jsoncat.core.springmvc.handler.GetRequestHandler;
import com.github.jsoncat.core.springmvc.handler.PostRequestHandler;
import com.github.jsoncat.core.springmvc.handler.RequestHandler;
import io.netty.handler.codec.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 保存请求请求处理器的窗口
 * @Author Goodenough
 * @Date 2021/3/7 14:20
 */
public class RequestHandlerFactory {

    public static final Map<HttpMethod, RequestHandler> REQUEST_HANDLERS = new HashMap<>();

    static {
        REQUEST_HANDLERS.put(HttpMethod.GET, new GetRequestHandler());
        REQUEST_HANDLERS.put(HttpMethod.POST, new PostRequestHandler());
    }

    public static RequestHandler get(HttpMethod httpMethod) {
        return REQUEST_HANDLERS.get(httpMethod);
    }
}
