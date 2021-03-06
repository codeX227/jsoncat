package com.github.jsoncat.exception;

/**
 * @Description 依赖注入bean时抛出的异常
 * @Author Goodenough
 * @Date 2021/3/6 15:18
 */
public class CanNotDetermineTargetBeanException extends RuntimeException {

    public CanNotDetermineTargetBeanException(String message){
        super(message);
    }
}
