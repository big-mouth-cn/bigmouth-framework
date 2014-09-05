package org.bigmouth.framework.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入。
 */
public class NumberUtils {

    private static Logger log = Logger.getLogger(NumberUtils.class);

    // 默认除法运算精度
    private static final int DEFAULT_DIV_SCALE = 2;

    // 这个类不能实例化
    private NumberUtils() {

    }

    // 中文金额单位数组
    final private static String[] straChineseUnit = new String[] { "分", "角", "圆", "拾", "佰", "仟", "万", "拾", "佰", "仟",
            "亿", "拾", "佰", "仟", "兆" };

    // 中文数字字符数组
    final private static String[] chineseNumber = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "点" };

    // 单位数组
    final private static String[] units = new String[] { "", "", "点", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百",
            "千", "兆" };

    // 中文大写数字数组
    final private static String[] numericChinese = new String[] { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "点" };

    /**
     * Description 将数字金额转换为中文金额 BigDecimal bigdMoneyNumber 转换前的数字金额 调用：myToChineseCurrency("101.89")="壹佰零壹圆捌角玖分"
     * myToChineseCurrency("100.89")="壹佰零捌角玖分" myToChineseCurrency("100")="壹佰圆整"
     * 
     * @param bigdMoneyNumber 数字
     * @return String 数字金额转换为中文金额
     */
    public static String toChineseCurrency(BigDecimal bigdMoneyNumber) {
        String strChineseCurrency = "";
        // 零数位标记
        boolean bZero = true;
        // 中文金额单位下标
        int ChineseUnitIndex = 0;
        try {
            if (bigdMoneyNumber.intValue() == 0)
                return "零圆整";
            // 处理小数部分，四舍五入
            double doubMoneyNumber = Math.round(bigdMoneyNumber.doubleValue() * 100);
            // 是否负数
            boolean bNegative = doubMoneyNumber < 0;
            // 取绝对值
            doubMoneyNumber = Math.abs(doubMoneyNumber);
            // 循环处理转换操作
            while (doubMoneyNumber > 0) {
                // 整的处理(无小数位)
                if (ChineseUnitIndex == 2 && strChineseCurrency.length() == 0) {
                    strChineseCurrency = strChineseCurrency + "整";
                }
                // 非零数位的处理
                if (doubMoneyNumber % 10 > 0) {
                    strChineseCurrency = chineseNumber[(int) doubMoneyNumber % 10] + straChineseUnit[ChineseUnitIndex]
                            + strChineseCurrency;
                    bZero = false;
                }
                // 零数位的处理
                else {
                    // 元的处理(个位)
                    if (ChineseUnitIndex == 2) {
                        // 段中有数字
                        if (doubMoneyNumber > 0) {
                            strChineseCurrency = straChineseUnit[ChineseUnitIndex] + strChineseCurrency;
                            bZero = true;
                        }
                    }
                    // 万、亿数位的处理
                    else if (ChineseUnitIndex == 6 || ChineseUnitIndex == 10) {
                        // 段中有数字
                        if (doubMoneyNumber % 1000 > 0) {
                            strChineseCurrency = straChineseUnit[ChineseUnitIndex] + strChineseCurrency;
                        }
                    }

                    // 前一数位非零的处理
                    if (!bZero) {
                        strChineseCurrency = chineseNumber[0] + strChineseCurrency;
                    }
                    bZero = true;
                }
                doubMoneyNumber = Math.floor(doubMoneyNumber / 10);
                ChineseUnitIndex++;
            }
            // 负数的处理
            if (bNegative) {
                strChineseCurrency = "负" + strChineseCurrency;
            }
        }
        catch (Exception e) {
            log.error(bigdMoneyNumber, e);
            return "";
        }
        return strChineseCurrency;
    }

    /**
     * 提供精确的加法运算。
     * 
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     * 
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     * 
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);

    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     * 
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */

    public static BigDecimal div(double v1, double v2) {
        return div(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     * 
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div(double v1, double v2, int scale) {
        if (v2 == 0)
            return new BigDecimal("0");
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);

    }

    /**
     * 提供精确的小数位四舍五入处理。
     * 
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double getRound(double v, int scale) {

        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    // 转换为中文的数字
    public static String toChineseNumber(BigDecimal bigdMoneyNumber, int type) {
        String[] numeric = null;
        if (type == 0) {
            numeric = numericChinese;
        }
        else {
            numeric = chineseNumber;
        }
        String strChineseCurrency = "";
        // 零数位标记
        boolean bZero = true;
        // 中文金额单位下标
        int ChineseUnitIndex = 0;
        try {
            if (bigdMoneyNumber.intValue() == 0)
                return "零";
            // 处理小数部分，四舍五入
            double doubMoneyNumber = Math.round(bigdMoneyNumber.doubleValue() * 100);
            // 是否负数
            boolean bNegative = doubMoneyNumber < 0;
            // 取绝对值
            doubMoneyNumber = Math.abs(doubMoneyNumber);
            // 循环处理转换操作
            while (doubMoneyNumber > 0) {
                // 整的处理(无小数位)
                if (ChineseUnitIndex == 2 && strChineseCurrency.length() == 0) {
                    strChineseCurrency = strChineseCurrency + "";
                }
                // 非零数位的处理
                if (doubMoneyNumber % 10 > 0) {
                    strChineseCurrency = numeric[(int) doubMoneyNumber % 10] + units[ChineseUnitIndex]
                            + strChineseCurrency;
                    bZero = false;
                }
                // 零数位的处理
                else {
                    // 元的处理(个位)
                    if (ChineseUnitIndex == 2) {
                        // 段中有数字
                        if (doubMoneyNumber > 0) {
                            strChineseCurrency = units[ChineseUnitIndex] + strChineseCurrency;
                            bZero = true;
                        }
                    }
                    // 万、亿数位的处理
                    else if (ChineseUnitIndex == 6 || ChineseUnitIndex == 10) {
                        // 段中有数字
                        if (doubMoneyNumber % 1000 > 0) {
                            strChineseCurrency = units[ChineseUnitIndex] + strChineseCurrency;
                        }
                    }

                    // 前一数位非零的处理
                    if (!bZero) {
                        strChineseCurrency = numeric[0] + strChineseCurrency;
                    }
                    bZero = true;
                }
                doubMoneyNumber = Math.floor(doubMoneyNumber / 10);
                ChineseUnitIndex++;
            }
            // 负数的处理
            if (bNegative) {
                strChineseCurrency = "负" + strChineseCurrency;
            }
        }
        catch (Exception e) {
            log.error(bigdMoneyNumber, e);
            return "";
        }
        if (strChineseCurrency.endsWith(units[2]))
            strChineseCurrency = strChineseCurrency.substring(0, strChineseCurrency.length() - 1);
        return strChineseCurrency;
    }

    /**
     * 提供把数串转为字符
     * 
     * @param value 转换的字符
     * @return 结果
     */
    public static String toString(int value) {
        try {
            return Integer.toString(value);
        }
        catch (NumberFormatException e) {
            return "0";
        }
    }

    public static String toString(float value) {
        try {
            return Float.toString(value);
        }
        catch (NumberFormatException e) {
            return "0";
        }
    }

    public static String toString(long value) {
        try {
            return Long.toString(value);
        }
        catch (NumberFormatException e) {
            return "0";
        }
    }

    /**
     * 提供把数串转为字符
     * 
     * @param value 转换的字符
     * @return 结果
     */
    public static String toString(double value) {
        try {
            return Double.toString(value);
        }
        catch (NumberFormatException e) {
            return "0";
        }
    }

    /**
     * @param value 数字
     * @return boolean 判断一个数是否为偶数
     */
    public static boolean isEven(int value) {
        return value % 2 == 0;
    }

    /**
     * 格式化输出数字
     * 
     * @param d 数字
     * @param sformat 格式 eg: "###,###.00"
     * @return String
     */
    public static String NumberFormat(double d, String sformat) {
        DecimalFormat df = new DecimalFormat(sformat);
        return df.format(d);
    }

    /**
     * 判断值的域是否在范围内
     * 
     * @param number 要判断的数字
     * @param min 最小数
     * @param max 最大数
     * @return true 是 false 否
     */
    public static boolean isBetween(double number, double min, double max) {
        return min <= number && number <= max;
    }

    public static boolean isBetween(long number, long min, long max) {
        return min <= number && number <= max;
    }

    public static boolean isBetween(int number, int min, int max) {
        return min <= number && number <= max;
    }

    /**
     * @param arrary 数组
     * @return 得到数组中最小的
     */
    public static int getMin(int[] arrary) {
        int imin = arrary[0];
        for (int x : arrary) {
            if (imin > x)
                imin = x;

        }
        return imin;
    }

    /**
     * @param arrary 数组
     * @return 得到数组中最小的
     */
    public static long getMin(long[] arrary) {
        long imin = arrary[0];
        for (long x : arrary) {
            if (imin > x)
                imin = x;
        }
        return imin;
    }

    /**
     * @param arrary 数组
     * @return 得到数组中最大的
     */
    public static int getMax(int[] arrary) {
        int imx = arrary[0];
        for (int x : arrary) {
            if (imx < x)
                imx = x;

        }
        return imx;
    }

    /**
     * @param arrary 数组
     * @return 得到数组中最大的
     */
    public static long getMax(long[] arrary) {
        long imx = arrary[0];
        for (long x : arrary) {
            if (imx < x)
                imx = x;

        }
        return imx;
    }

    public static int compare(double lhs, double rhs) {
        if (lhs < rhs) {
            return -1;
        }
        if (lhs > rhs) {
            return +1;
        }
        long lhsBits = Double.doubleToLongBits(lhs);
        long rhsBits = Double.doubleToLongBits(rhs);
        if (lhsBits == rhsBits) {
            return 0;
        }
        if (lhsBits < rhsBits) {
            return -1;
        }
        else {
            return +1;
        }
    }

    public static int compare(float lhs, float rhs) {
        if (lhs < rhs) {
            return -1;
        }
        if (lhs > rhs) {
            return +1;
        }
        // Need to compare bits to handle 0.0 == -0.0 being true
        // compare should put -0.0 < +0.0
        // Two NaNs are also == for compare purposes
        // where NaN == NaN is false
        int lhsBits = Float.floatToIntBits(lhs);
        int rhsBits = Float.floatToIntBits(rhs);
        if (lhsBits == rhsBits) {
            return 0;
        }
        // Something exotic! A comparison to NaN or 0.0 vs -0.0
        // Fortunately NaN's int is > than everything else
        // Also negzeros bits < poszero
        // NAN: 2143289344
        // MAX: 2139095039
        // NEGZERO: -2147483648
        if (lhsBits < rhsBits) {
            return -1;
        }
        else {
            return +1;
        }
    }

    static private final String lin = "000000000000000000000000000000000000";

    public static String getCutLength(int value, int keyLength) {
        return getCutLength(((Integer) value).toString(), keyLength);
    }

    public static String getCutLength(String value, int keyLength) {
        StringBuilder stemp = new StringBuilder();
        stemp.append(lin).append(value);
        return stemp.substring(stemp.length() - keyLength, stemp.length());
    }

    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static boolean isBigDecimal(String str) {
        java.util.regex.Matcher match = null;
        if (isNumber(str) == true) {
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[0-9]*");
            match = pattern.matcher(str.trim());
        }
        else {
            if (str.trim().indexOf(".") == -1) {
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^[+-]?[0-9]*");
                match = pattern.matcher(str.trim());
            }
            else {
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^[+-]?[0-9]+(\\.\\d{1,100}){1}");
                match = pattern.matcher(str.trim());
            }
        }
        return match.matches();
    }

}