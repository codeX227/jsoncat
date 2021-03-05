package com.github.jsoncat.core.config;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * @Description 读取配置文件类的接口
 * @Author Goodenough
 * @Date 2021/3/5 20:52
 */
public interface Configuration {

    //默认配置文件名称
    String[] DEFAULT_CONFIG_NAMES = {"application.properties", "application.yaml"};

    int getInt(String id);

    String getString(String id);

    Boolean getBoolean(String id);

    default void put(String id, String content){
    }

    default void putAll(Map<String, String> map){
    }

    default void loadResources(List<Path> resourcePaths){
    }
}
