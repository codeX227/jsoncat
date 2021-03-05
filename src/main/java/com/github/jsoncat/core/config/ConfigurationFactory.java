package com.github.jsoncat.core.config;

/**
 * @Description 工厂类，创建可以读取配置文件的对象
 * @Author Goodenough
 * @Date 2021/3/5 21:09
 */
public class ConfigurationFactory {

    public static Configuration getConfig(){
        return SingleConfigurationHolder.INSTANCE;
    }

    private static class SingleConfigurationHolder{

        private static final Configuration INSTANCE = buildConfiguration();

        private static Configuration buildConfiguration(){
            return new DefaultConfiguration();
        }
    }
}
