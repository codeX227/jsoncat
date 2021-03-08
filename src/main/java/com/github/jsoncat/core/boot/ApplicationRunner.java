package com.github.jsoncat.core.boot;

/**
 * @Description
 * @Author Goodenough
 * @Date 2021/3/7 13:40
 */
@FunctionalInterface
public interface ApplicationRunner {

    /**
     * After initialization is complete, perform some things
     */
    void run();
}
