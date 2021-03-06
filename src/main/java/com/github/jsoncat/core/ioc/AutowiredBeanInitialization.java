package com.github.jsoncat.core.ioc;

import com.github.jsoncat.annotation.config.Value;
import com.github.jsoncat.annotation.ioc.Autowired;
import com.github.jsoncat.annotation.ioc.Qualifier;
import com.github.jsoncat.common.util.ObjectUtil;
import com.github.jsoncat.common.util.ReflectionUtil;
import com.github.jsoncat.core.aop.factory.AopProxyBeanPostProcessorFactory;
import com.github.jsoncat.core.aop.intercept.BeanPostProcessor;
import com.github.jsoncat.core.config.ConfigurationManager;
import com.github.jsoncat.exception.CanNotDetermineTargetBeanException;
import com.github.jsoncat.exception.InterfaceNotHaveImplementedClassException;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description bean属性注入
 * @Author Goodenough
 * @Date 2021/3/6 14:37
 */
public class AutowiredBeanInitialization {

    private final String[] packageNames;

    // 二级缓存，解决循环依赖
    private static final Map<String, Object> SINGLE_OBJECTS = new ConcurrentHashMap<>(64);

    public AutowiredBeanInitialization(String[] packageNames) {
        this.packageNames = packageNames;
    }

    public void initialize(Object beanInstance) {
        Class<?> beanClass = beanInstance.getClass();
        Field[] beanFields = beanClass.getDeclaredFields();
        // 遍历 bean 的属性
        if (beanFields.length >0) {
            for (Field beanField : beanFields) {
                if (beanField.isAnnotationPresent(Autowired.class)) {
                    Object beanFieldInstance = processAutowiredAnnotationField(beanField);
                    String beanFieldName = BeanHelper.getBeanName(beanField.getType());
                    // 解决循环依赖
                    beanFieldInstance = resolveCircularDependency(beanInstance, beanFieldInstance, beanFieldName);
                    // AOP
                    BeanPostProcessor beanPostProcessor = AopProxyBeanPostProcessorFactory.get(beanField.getType());
                    beanFieldInstance = beanPostProcessor.postProcessAfterInitialization(beanFieldInstance);
                    ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                }
                if (beanField.isAnnotationPresent(Value.class)) {
                    Object convertedValue = processValueAnnotationField(beanField);
                    ReflectionUtil.setField(beanInstance, beanField, convertedValue);
                }
            }
        }
    }

    /**
     * 获得被 @Autowired 标记的字段的 bean
     * @param beanField 目标类的字段
     * @return 目标类字段对应的对象
     */
    private Object processAutowiredAnnotationField(Field beanField) {
        Class<?> beanFieldClass = beanField.getType();
        String beanFieldName = BeanHelper.getBeanName(beanFieldClass);
        Object beanFieldInstance;
        if (beanFieldClass.isAnnotation()) {
            @SuppressWarnings("unchecked")
            Set<Class<?>> subClasses = ReflectionUtil.getSubClass(packageNames, (Class<Object>) beanFieldClass);
            if (subClasses.size() == 0)
                throw new InterfaceNotHaveImplementedClassException(beanFieldClass.getName()+"is interface and do not have implemented class exception");
            if (subClasses.size() == 1) {
                Class<?> subClass = subClasses.iterator().next();
                beanFieldName = BeanHelper.getBeanName(subClass);
            }
            if (subClasses.size() > 1){
                Qualifier qualifier = beanField.getDeclaredAnnotation(Qualifier.class);
                beanFieldName = qualifier == null ? beanFieldName : qualifier.value();
            }
        }
        beanFieldInstance = BeanFactory.BEANS.get(beanFieldName);
        if (beanFieldInstance == null) {
            throw new CanNotDetermineTargetBeanException("can not determine target bean of"+beanFieldClass.getName());
        }
        return beanFieldInstance;
    }

    /**
     * 解决循环依赖
     * @param beanInstance 要解决循环依赖所在的 bean
     * @param beanFieldInstance 要解决循环依赖的字段实例
     * @param beanFieldName 字段的 beanName
     * @return Object 字段实例
     */
    private Object resolveCircularDependency(Object beanInstance, Object beanFieldInstance, String beanFieldName) {
        if (SINGLE_OBJECTS.containsKey(beanFieldName)) {
            beanFieldInstance = SINGLE_OBJECTS.get(beanFieldName);
        } else {
            SINGLE_OBJECTS.put(beanFieldName, beanFieldInstance);
            // TODO: 2021/3/6 循环依赖传参
            initialize(beanInstance);
        }
        return beanFieldInstance;
    }

    /**
     * 处理被 @Value 注解标记的字段
     * @param beanField 目标类的字段
     * @return 目标类的字段对应的对象
     */
    private Object processValueAnnotationField(Field beanField) {
        String key = beanField.getDeclaredAnnotation(Value.class).value();
        ConfigurationManager configurationManager = (ConfigurationManager) BeanFactory.BEANS.get(ConfigurationManager.class.getName());
        String value = configurationManager.getString(key);
        if (value == null)
            throw new IllegalArgumentException("can not find target value for property:{" + key + "}");
        return ObjectUtil.convert(beanField.getType(), value);
    }
}
