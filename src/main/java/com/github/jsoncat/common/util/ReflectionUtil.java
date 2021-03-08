package com.github.jsoncat.common.util;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import javax.validation.ConstraintViolationException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @Description 反射通用方法
 * @Author Goodenough
 * @Date 2021/3/5 15:57
 */
@Slf4j
public class ReflectionUtil {

    /**
     * 扫描指定包下有指定注解的类
     * @param packageNames 指定的包名
     * @param annotation 指定的注解
     * @return 指定包下标记了指定注解的类的 class 对象集合
     */
    public static Set<Class<?>> scanAnnotatedClass(String[] packageNames, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(packageNames, new TypeAnnotationsScanner());
        Set<Class<?>> annotatedClass = reflections.getTypesAnnotatedWith(annotation, true);
        log.info("The number of class Annotated with @" + annotation.getSimpleName() + ":[{}]", annotatedClass.size());
        return annotatedClass;
    }

    /**
     * 获得指定接口的实现类
     * @param packageNames 指定包名
     * @param interfaceClass 指定接口
     * @return 实现了指定接口的子类集合
     */
    public static <T> Set<Class<? extends T>> getSubClass(Object[] packageNames, Class<T> interfaceClass) {
        Reflections reflections = new Reflections(packageNames);
        return reflections.getSubTypesOf(interfaceClass);

    }

    /**
     * 通过类型创建实例对象
     * @param cls 目标类型
     * @return 通过目标类型创建的实例对象
     */
    public static Object newInstance(Class<?> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * 设置指定对象中指定域的值
     * @param obj 目标对象
     * @param field 目标字段
     * @param value 要设置的值
     */
    public static void setField(Object obj, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalAccessException impossible) {
            throw new AssertionError(impossible);
        }

    }

    /**
     * 执行指定方法
     * @param targetObject 方法所属对象
     * @param method 目标方法
     * @param args 方法的参数
     * @return 方法执行结果
     */
    public static Object executeTargetMethod(Object targetObject, Method method, Object... args) {
        try {
            return method.invoke(targetObject, args);
        } catch (Throwable t) {
            if (t.getCause() != null && t.getCause() instanceof ConstraintViolationException) {
                throw (ConstraintViolationException) t.getCause();
            }
        }
        return null;
    }

    /**
     * 执行指定的无返回值的方法
     * @param targetObject 方法所属对象
     * @param method 目标方法
     * @param args 方法所需参数
     */
    public static void executeTargetMethodNoResult(Object targetObject, Method method, Object... args) {
        try {
            // invoke target method through reflection
            method.invoke(targetObject, args);
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
    }
}
