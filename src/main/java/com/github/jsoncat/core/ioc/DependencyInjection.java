package com.github.jsoncat.core.ioc;

import java.util.Map;

/**
 * @Description
 * @Author Goodenough
 * @Date 2021/3/6 14:36
 */
public class DependencyInjection {

    /**
     * 遍历ioc容器所有bean的属性, 为所有带@Autowired/@Value注解的属性注入实例
     */
    public static void inject(String[] packageNames) {
        AutowiredBeanInitialization autowiredBeanInitialization = new AutowiredBeanInitialization(packageNames);
        Map<String, Object> beans = BeanFactory.BEANS;
        if (beans.size() > 0) {
            BeanFactory.BEANS.values().forEach(autowiredBeanInitialization::initialize);
        }
    }
}
