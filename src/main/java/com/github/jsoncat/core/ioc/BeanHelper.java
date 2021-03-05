package com.github.jsoncat.core.ioc;

import com.github.jsoncat.annotation.ioc.Component;

/**
 * @Description bean工具类
 * @Author Goodenough
 * @Date 2021/3/5 20:38
 */
public class BeanHelper {

    /**
     * 如果 Component 注解申明了 name，beanName 就是 Component 的值，
     * 没申明 name，beanName 就是类全限定名
     */
    public static String getBeanName(Class<?> aClass){
        String beanName = aClass.getName();
        if (aClass.isAnnotationPresent(Component.class)) {
            Component component = aClass.getAnnotation(Component.class);
            beanName = "".equals(component.name()) ? aClass.getName() : component.name();
        }
        return beanName;
    }
}
