package com.github.jsoncat.factory;

import com.github.jsoncat.annotation.aop.Aspect;
import com.github.jsoncat.annotation.ioc.Component;
import com.github.jsoncat.annotation.springmvc.RestController;
import com.github.jsoncat.common.util.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 加载组件类的工厂
 * @Author Goodenough
 * @Date 2021/3/5 16:43
 */
public class ClassFactory {

    public static final Map<Class<? extends Annotation>, Set<Class<?>>> CLASSES = new ConcurrentHashMap<>();

    public static void loadClass(String[] packageName) {

        Set<Class<?>> restControllers = ReflectionUtil.scanAnnotatedClass(packageName, RestController.class);
        Set<Class<?>> components = ReflectionUtil.scanAnnotatedClass(packageName, Component.class);
        Set<Class<?>> aspects = ReflectionUtil.scanAnnotatedClass(packageName, Aspect.class);
        CLASSES.put(RestController.class, restControllers);
        CLASSES.put(Component.class, components);
        CLASSES.put(Aspect.class, aspects);
    }
}
