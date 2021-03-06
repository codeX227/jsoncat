package com.github.jsoncat.exception;

/**
 * @Description 接口无实现类异常
 * @Author Goodenough
 * @Date 2021/3/6 15:08
 */
public class InterfaceNotHaveImplementedClassException extends RuntimeException {

    public InterfaceNotHaveImplementedClassException(String message) {
        super(message);
    }
}
