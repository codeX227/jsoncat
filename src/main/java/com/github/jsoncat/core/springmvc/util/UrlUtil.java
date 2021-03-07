package com.github.jsoncat.core.springmvc.util;

import io.netty.handler.codec.http.QueryStringDecoder;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;

/**
 * @Description 处理请求 url 的工具类
 * @Author Goodenough
 * @Date 2021/3/7 14:51
 */
public class UrlUtil {

    /**
     *获得 uri 的解码路径
     * @param uri String uri
     * @return String requestPath
     */
    public static String getRequestPath(String uri) {
        QueryStringDecoder queryDecoder = new QueryStringDecoder(uri, Charsets.toCharset(CharEncoding.UTF_8));
        return queryDecoder.path();
    }
}
