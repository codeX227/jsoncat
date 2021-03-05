package com.github.jsoncat.core.config.resource.property;

import com.github.jsoncat.core.config.resource.AbstractResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Description 加载 properties 文件的资源加载器
 * @Author Goodenough
 * @Date 2021/3/5 21:28
 */
public class PropertiesResourceLoader extends AbstractResourceLoader {


    @Override
    protected Map<String, String> loadResources(Path path) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(path); Reader reader = new InputStreamReader(inputStream)){
            properties.load(reader);
        }
        Map<String, String> resource = new HashMap<>(properties.size());
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            resource.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return resource;
    }
}
