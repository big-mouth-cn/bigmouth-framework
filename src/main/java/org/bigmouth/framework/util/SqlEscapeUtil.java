package org.bigmouth.framework.util;

/**
 * SQL中的字符转译
 * 
 */
public class SqlEscapeUtil {

    /*
     * 模糊查询Like '%?%'查询时 当参数?为‘时候，出现’不配对的异常 当参数为_时候，模糊查询到所有结果，并不是包含_的结果 当参数为%时候，模糊查询到所有结果，并不是包含%的结果
     */
    public static void main(String[] arg) {
        String condition = "asdf_";
        String strbuff = getTranslateStr(condition);
        System.out.println(strbuff);
    }

    public static String getTranslateStr(String condition) {
        StringBuffer temp = new StringBuffer();
        temp.append(condition.replaceAll("\'", "\'\'").replaceAll("_", "/_").replaceAll("%", "/%"));
        return temp.toString();
    }
}
