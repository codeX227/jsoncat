package com.github.jsoncat.core.ioc;

import com.github.jsoncat.annotation.ioc.Component;
import com.github.jsoncat.annotation.springmvc.RestController;
import com.github.jsoncat.common.util.ReflectionUtil;
import com.github.jsoncat.core.config.ConfigurationFactory;
import com.github.jsoncat.core.config.ConfigurationManager;
import com.github.jsoncat.factory.ClassFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 创建 bean 的工厂
 * @Author Goodenough
 * @Date 2021/3/5 20:34
 */
public final class BeanFactory {

    //ioc 容器
    public static final Map<String, Object> BEANS =new ConcurrentHashMap<>();

    public static void loadBeans(){
        Set<Class<?>> componentClasses = ClassFactory.CLASS.get(Component.class);
        for (Class<?> aClass : componentClasses) {
            String beanName = BeanHelper.getBeanName(aClass);
            Object instance = ReflectionUtil.newInstance(aClass);
            BEANS.put(beanName, instance);
        }
        Set<Class<?>> restControllerClasses = ClassFactory.CLASS.get(RestController.class);
        for (Class<?> aClass : restControllerClasses) {
            Object instance = ReflectionUtil.newInstance(aClass);
            BEANS.put(aClass.getName(), instance);
        }
        BEANS.put(ConfigurationManager.class.getName(), new ConfigurationManager(ConfigurationFactory.getConfig()));
    }
}
