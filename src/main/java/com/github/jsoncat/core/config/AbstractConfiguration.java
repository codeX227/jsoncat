package com.github.jsoncat.core.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 实现基本配置缓存的存入和读取操作
 * @Author Goodenough
 * @Date 2021/3/5 21:00
 */
public class AbstractConfiguration implements Configuration{

    /**
     * 保存配置信息的缓存
     */
    private static final Map<String, String> CONFIGURATION_CACHE = new ConcurrentHashMap<>(64);

    @Override
    public int getInt(String id) {
        String result = CONFIGURATION_CACHE.get(id);
        return Integer.parseInt(result);
    }

    @Override
    public String getString(String id) {
        return CONFIGURATION_CACHE.get(id);
    }

    @Override
    public Boolean getBoolean(String id) {
        String result = CONFIGURATION_CACHE.get(id);
        return Boolean.parseBoolean(result);
    }

    @Override
    public void put(String id, String content) {
        CONFIGURATION_CACHE.put(id, content);
    }

    @Override
    public void putAll(Map<String, String> maps) {
        CONFIGURATION_CACHE.putAll(maps);
    }
}
