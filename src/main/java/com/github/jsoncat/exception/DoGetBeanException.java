package com.github.jsoncat.exception;

/**
 * @Description 从 ioc 容器未获取到 bean 时抛出的异常
 * @Author Goodenough
 * @Date 2021/3/5 22:46
 */
public class DoGetBeanException extends RuntimeException {

    public DoGetBeanException(String message) {
        super(message);
    }
}
