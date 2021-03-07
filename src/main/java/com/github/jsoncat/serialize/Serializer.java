package com.github.jsoncat.serialize;

/**
 * @Description 序列化
 * @Author Goodenough
 * @Date 2021/3/7 17:01
 */
public interface Serializer {

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
