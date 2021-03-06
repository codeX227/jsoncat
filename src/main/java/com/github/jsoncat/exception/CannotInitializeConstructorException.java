package com.github.jsoncat.exception;

/**
 * @Description 不能初始化构造函数
 * @Author Goodenough
 * @Date 2021/3/6 13:28
 */
public class CannotInitializeConstructorException extends RuntimeException{

    public CannotInitializeConstructorException(String message){
        super(message);
    }
}
