package com.github.jsoncat.core.springmvc.factory;

import com.github.jsoncat.common.util.ReflectionUtil;
import com.github.jsoncat.exception.ErrorResponse;
import com.github.jsoncat.serialize.impl.JacksonSerializer;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.AsciiString;

import java.lang.reflect.Method;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @Description 执行请求对应的方法，并响应给客户端
 * @Author Goodenough
 * @Date 2021/3/7 16:56
 */
public class FullHttpResponseFactory {

    private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");
    private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");
    private static final JacksonSerializer JSON_SERIALIZER = new JacksonSerializer();

    /**
     * 创建错误响应对象
     * @param url 请求错误的 url
     * @param message 错误信息
     * @param httpResponseStatus 响应状态对象
     * @return FullHttpResponse 响应对象
     */
    public static FullHttpResponse getErrorResponse(String url, String message, HttpResponseStatus httpResponseStatus) {
        ErrorResponse errorResponse = new ErrorResponse(httpResponseStatus.code(), httpResponseStatus.reasonPhrase(), message, url);
        byte[] content = JSON_SERIALIZER.serialize(errorResponse);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, httpResponseStatus, Unpooled.wrappedBuffer(content));
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }

    /**
     * 执行 controller 方法并响应
     * @param targetMethod 要执行的 controller 方法
     * @param targetMethodParams 方法参数
     * @param targetObject 要执行方法的 controller 对象
     * @return FullHttpResponse 响应对象
     */
    public static FullHttpResponse getSuccessResponse(Method targetMethod, List<Object> targetMethodParams, Object targetObject) {
        //the return type of targetMethod is void
        if (targetMethod.getReturnType() == void.class) {
            ReflectionUtil.executeTargetMethodNoResult(targetObject, targetMethod, targetMethodParams.toArray());
            return buildSuccessResponse();
        } else {
            Object result = ReflectionUtil.executeTargetMethod(targetObject, targetMethod, targetMethodParams.toArray());
            return buildSuccessResponse(result);
        }
    }

    /**
     * 创建有响应内容的 Success 响应对象
     */
    private static FullHttpResponse buildSuccessResponse(Object o) {
        byte[] content = JSON_SERIALIZER.serialize(o);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(content));
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }

    /**
     * 创建无响应内容的 Success 响应对象
     */
    private static FullHttpResponse buildSuccessResponse() {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK);
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }

}
