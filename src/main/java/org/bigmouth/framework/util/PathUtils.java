/*
 * 文件名称: PathUtils.java
 * 版权信息: Copyright 2005-2012 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: allen.hu
 * 修改日期: 2012-9-14
 * 修改内容: 
 */
package org.bigmouth.framework.util;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 路径工具类.
 * 
 * @author Allen / 2012-9-14
 */
public class PathUtils {

    private static Set<Character> invalidChar = new HashSet<Character>();

    static {
        invalidChar.add('*');
        invalidChar.add('"');
        invalidChar.add('<');
        invalidChar.add('>');
        invalidChar.add('|');
        invalidChar.add(' ');
        invalidChar.add('\t');
    }
    
    public static boolean existsOfFolder(File folder) {
        if (!folder.exists())
            return folder.mkdirs();
        return true;
    }
    
    public static boolean existsOfFile(File file) throws IOException {
        if (!file.exists()) 
            return file.createNewFile();
        return true;
    }

    public static boolean isEqualsPath(String path1, String path2) {
        path1 = trimEndFileSeparator(path1).toLowerCase();
        path2 = trimEndFileSeparator(path2).toLowerCase();
        return path1.equals(path2);
    }

    public static boolean isNotEqualsPath(String path1, String path2) {
        return !isEqualsPath(path1, path2);
    }

    /**
     * 路径预处理, 替换路径分隔符为统一的"/", trim头尾空格, 如果路径不以"/"开头, 则添加"/"
     * 
     * @param path 目录路径
     * @return 预处理后的目录路径
     */
    public static String appendBeginFileSeparator(String path) {
        String afterPath = replacePathSeparator(path);
        if (!afterPath.startsWith("/")) {
            afterPath = "/" + afterPath;
        }
        return afterPath;
    }

    /**
     * 路径预处理, 替换路径分隔符为统一的"/", trim头尾空格, 截去开头的"/"
     * 
     * @param path 目录路径
     * @return 预处理后的目录路径
     */
    public static String trimBeginFileSeparator(String path) {
        String afterPath = replacePathSeparator(path);
        while (afterPath.startsWith("/")) {
            afterPath = afterPath.substring(1);
        }
        return afterPath;
    }

    /**
     * 路径预处理, 替换路径分隔符为统一的"/", trim头尾空格, 如果路径不以"/"结尾, 则追加"/"
     * 
     * @param path 目录路径
     * @return 预处理后的目录路径
     */
    public static String appendEndFileSeparator(String path) {
        String afterPath = replacePathSeparator(path);
        if (!afterPath.endsWith("/")) {
            afterPath = afterPath + "/";
        }
        return afterPath;
    }

    /**
     * 路径预处理, 替换路径分隔符为统一的"/", trim头尾空格, 截去末尾的"/"
     * 
     * @param path 目录路径
     * @return 预处理后的目录路径
     */
    public static String trimEndFileSeparator(String path) {
        String afterPath = replacePathSeparator(path);
        while (afterPath.endsWith("/")) {
            afterPath = afterPath.substring(0, afterPath.length() - 1);
        }
        return afterPath;
    }

    public static String replacePathSeparator(String path) {
        return path.trim().replaceAll("\\\\", "/");
    }

    /**
     * 判断指定路径是否包含非法字符
     * 
     * @param path 路径
     * @return
     */
    public static boolean containsInvalidCharacter(String path) {
        char[] charArray = path.trim().toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (invalidChar.contains(charArray[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取ClassPath路径
     * 
     * @return ClassPath路径
     */
    public static String getClassPath() {
        return PathUtils.class.getResource("/").getPath();
    }

    /**
     * 获取WebInf路径
     * 
     * @return WebInf路径
     */
    public static String getWebInfPath() {
        return StringUtils.substringBeforeLast(StringUtils.substringBeforeLast(getClassPath(), "/"), "/");
    }

    /**
     * 获取WebRoot路径
     * 
     * @return WebRoot路径
     */
    public static String getWebRootPath() {
        return StringUtils.substringBeforeLast(getWebInfPath(), "/");
    }
}
