package com.github.jsoncat.core.config.resource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * @Description
 * @Author Goodenough
 * @Date 2021/3/5 21:24
 */
public abstract class AbstractResourceLoader implements ResourceLoader{
    @Override
    public Map<String, String> loadResource(Path path) throws IOException {
        return loadResources(path);
    }

    protected abstract Map<String, String> loadResources(Path path) throws IOException;
}
