/*
 * 文件名称: ArrayUtils.java
 * 版权信息: Copyright 2012 Big-mouth framework All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2012-6-29
 * 修改内容: 
 */
package org.bigmouth.framework.util;

import java.util.Arrays;

/**
 * 数组工具。
 * 
 * @author Allen.Hu / 2012-6-29
 * @since Bigmouth-Framework 1.0
 */
public class ArrayUtils {

    /**
     * Private Construction
     */
    private ArrayUtils() {
    }

    /**
     * 判断该对象（数组）是否为空。
     * 
     * @param obj
     * @return true: 对象为空; false: 对象非空
     * @author Allen.Hu / 2012-6-29 
     * @since Bigmouth-Framework 1.0
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        Class<?> cls = obj.getClass();
        if (cls.isArray()) {
            if (cls.equals(int[].class)) {
                return ((int[]) obj).length == 0;
            }
            else if (cls.equals(long[].class)) {
                return ((long[]) obj).length == 0;
            }
            else if (cls.equals(byte[].class)) {
                return ((byte[]) obj).length == 0;
            }
            else if (cls.equals(short[].class)) {
                return ((short[]) obj).length == 0;
            }
            else if (cls.equals(float[].class)) {
                return ((float[]) obj).length == 0;
            }
            else if (cls.equals(double[].class)) {
                return ((double[]) obj).length == 0;
            }
            else if (cls.equals(boolean[].class)) {
                return ((boolean[]) obj).length == 0;
            }
            else if (cls.equals(char[].class)) {
                return ((char[]) obj).length == 0;
            }
            else {
                return CollectionUtils.isEmpty(Arrays.asList(obj));
            }
        }

        // 对非数组类型, 默认是空的
        return true;
    }

    /**
     * 判断对象（数组）是否非空。
     * 
     * @param obj
     * @return true: 对象非空; false: 对象空
     * @author Allen.Hu / 2012-6-29 
     * @since Bigmouth-Framework 1.0
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 将数组转换为字符串，元素以,分隔。
     * 
     * @param array 数组
     * @return
     * @author Allen.Hu / 2012-6-29 
     * @since Bigmouth-Framework 1.0
     */
    public static String toString(String[] array) {
        StringBuffer sb = new StringBuffer();
        if (isNotEmpty(array)) {
            for (String elem : array) {
                sb.append(elem).append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 将数组转换为字符串，元素以,分隔。
     * 
     * @param <T> 数组元素类型
     * @param array
     * @return
     * @author Allen.Hu / 2012-6-29 
     * @since Bigmouth-Framework 1.0
     */
    public static <T> String toString(T[] array) {
        StringBuffer sb = new StringBuffer();
        if (isNotEmpty(array)) {
            for (T elem : array) {
                sb.append(elem).append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
