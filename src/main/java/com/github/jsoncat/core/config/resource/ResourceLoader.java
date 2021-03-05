package com.github.jsoncat.core.config.resource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * @Description 资源文件加载
 * @Author Goodenough
 * @Date 2021/3/5 21:22
 */
public interface ResourceLoader {

    Map<String, String> loadResource(Path path) throws IOException;
}
