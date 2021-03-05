package com.github.jsoncat.core.config.resource.yaml;

import com.github.jsoncat.core.config.resource.AbstractResourceLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * @Description 读取 yaml 配置文件的资源加载器
 * @Author Goodenough
 * @Date 2021/3/5 21:43
 */
public class YamlResourceLoader extends AbstractResourceLoader {

    @Override
    protected Map<String, String> loadResources(Path path) throws IOException {
        return null;
    }
}
