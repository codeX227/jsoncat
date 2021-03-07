package com.github.jsoncat.core.springmvc.handler;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @Description 请求处理器
 * @Author Goodenough
 * @Date 2021/3/7 14:21
 */
public interface RequestHandler {

    FullHttpResponse handle(FullHttpRequest fullHttpRequest);
}
