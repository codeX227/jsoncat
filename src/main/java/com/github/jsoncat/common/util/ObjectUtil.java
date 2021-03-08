package com.github.jsoncat.common.util;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

/**
 * @Description 配置文件属性编辑器
 * @Author Goodenough
 * @Date 2021/3/5 15:46
 */
public class ObjectUtil {

    /**
     * 将字符串转换为目标类型
     * @param targetType 要转换的目标类型
     * @param s 要转换的字符串
     * @throws NumberFormatException When string to number, if string is not a number,then throw NumberFormatException
     */
    public static Object convert(Class<?> targetType, String s) {
        PropertyEditor editor = PropertyEditorManager.findEditor(targetType);
        editor.setAsText(s);
        return editor.getValue();
    }
}
