/*
 * 文件名称: StringUtils.java
 * 版权信息: Copyright 2012 Big-mouth framework All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2012-7-6
 * 修改内容: 
 */
package org.bigmouth.framework.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串工具栏。
 * 
 * @author Allen.Hu / 2012-7-6
 * @since Bigmouth-Framework 1.0
 */
public class StringUtils {

    private StringUtils() {
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * <p>
     * 将银行卡明文进行加密显示。 如果银行卡长度小于8位，则不进行加密显示。
     * </p>
     * 
     * <pre>
     * StrHelper.toBankCard("6225 0021 2716 2529"); = "6225 **** **** 2529",
     * StrHelper.toBankCard("6225 00212716 2529"); = "6225 ******** 2529",
     * StrHelper.toBankCard("6225002127162529"); = "6225********2529"
     * </pre>
     * 
     * @param card
     * @return
     * @throws Exception
     */
    public static String toBankCard(String card) {
        String[] temp = org.apache.commons.lang.StringUtils.split(card, " ");
        if (temp.length > 1) {
            StringBuffer rs = new StringBuffer();
            for (int i = 0; i < temp.length; i++) {
                if (i == 0) {
                    rs.append(temp[i]);
                }
                else if (i == temp.length - 1) {
                    rs.append(temp[i]);
                }
                else {
                    StringBuffer t = new StringBuffer();
                    for (int j = 0; j < temp[i].length(); j++) {
                        t.append("*");
                    }
                    rs.append(t.toString());
                }
                if (i < temp.length - 1) {
                    rs.append(" ");
                }
            }
            return rs.toString();
        }
        else {
            if (card.length() > 8) {
                StringBuffer rs = new StringBuffer();
                String start = card.substring(0, 4);
                String end = card.substring(card.length() - 4, card.length());
                int length = card.length() - 8;
                StringBuffer center = new StringBuffer();
                for (int i = 0; i < length; i++) {
                    center.append("*");
                }
                return rs.append(start).append(center.toString()).append(end).toString();
            }
            else {
                return card;
            }
        }
    }

    /**
     * <p>
     * 将单位为分的金额转换为指定格式以元为单位的金额
     * </p>
     * 
     * <pre>
     * StrHelper.toYuanMoneyFormat(1000000); = "10,000.00"
     * </pre>
     * 
     * @param money
     * @return
     */
    public static String toYuanMoneyFormat(long money) {
        if (money > 0) {
            double v = (double) money / (double) 100;
            BigDecimal diff = new BigDecimal(v);
            double value = diff.setScale(2, BigDecimal.ROUND_UP).doubleValue();
            return toMoneyFormat(value);
        }
        return "0";
    }

    /**
     * <p>
     * 将指定金额转换为<code>"##,###.00"</code>格式并返回。
     * </p>
     * 
     * @param money
     * @return
     */
    public static String toMoneyFormat(double money) {
        return toMoneyFormat(money, "##,###.00");
    }

    /**
     * <p>
     * 将指定金额转换为指定格式并返回。
     * </p>
     * 
     * @param money
     * @param pattern
     * @return
     */
    public static String toMoneyFormat(double money, String pattern) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern(pattern);
        return df.format(money);
    }

    /**
     * <p>
     * 使用指定编码格式将字符串进行编码，并返回新的值。<br />
     * 默认使用<code>UTF-8</code>格式进行编码。
     * </p>
     * 
     * @param value 字符串值
     * @return 编码后的字符串
     * @throws UnsupportedEncodingException
     * @author Allen.Hu / 2012-7-30
     * @since SkyMarket 1.0
     */
    public static String getEncodedString(String value) throws UnsupportedEncodingException {
        return StringUtils.getEncodedString(value, "UTF-8");
    }

    /**
     * <p>
     * 使用指定编码格式将字符串进行编码，并返回新的值。
     * </p>
     * 
     * @param value 字符串值
     * @param enc 编码格式
     * @return 编码后的字符串
     * @throws UnsupportedEncodingException
     * @author Allen.Hu / 2012-7-30
     * @since SkyMarket 1.0
     */
    public static String getEncodedString(String value, String enc) throws UnsupportedEncodingException {
        return new String(value.getBytes("ISO-8859-1"), enc);
    }

    /**
     * 是否为空字符串
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0 || str.trim().equalsIgnoreCase("null");
    }

    /**
     * 是否为空字符串
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String arrayToString(String[] paramArrayOfString) {
        return arrayToString(paramArrayOfString, "|");
    }

    public static String arrayToString(String[] paramArrayOfString, String paramString) {
        if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
            return "";
        }
        if (paramString == null) {
            paramString = "";
        }
        StringBuffer localStringBuffer = new StringBuffer("");
        int i = paramArrayOfString.length;
        for (int j = 0; j < i - 1; ++j) {
            localStringBuffer.append((paramArrayOfString[j] == null) ? "" : paramArrayOfString[j]).append(paramString);
        }
        localStringBuffer.append((paramArrayOfString[(i - 1)] == null) ? "" : paramArrayOfString[(i - 1)]);

        return localStringBuffer.toString();
    }

    /**
     * 判断字符串是否为数字函数，正则表达式
     */
    public static boolean isDigitalChar(String strNumber) {
        if (isEmpty(strNumber))
            return false;

        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(strNumber);
        return !m.find();
    }

    public static String getString(Object strValue) {
        return getString(strValue, "");
    }

    public static String getString(Object strValue, String replaceIfNull) {
        try {
            if (strValue == null)
                return replaceIfNull;
            else
                return strValue.toString();
        }
        catch (Exception ex) {
            return "";
        }

    }

    public static String expandStr(String str, int len, char ch, boolean fillOnLeft) {
        int nLen = str.length();
        if (len <= nLen)
            return str;
        String sRet = str;
        for (int i = 0; i < len - nLen; i++)
            sRet = fillOnLeft ? String.valueOf(ch) + String.valueOf(sRet) : String.valueOf(sRet) + String.valueOf(ch);

        return sRet;
    }

    public static String setEndswith(String str, String ch) {
        if (str == null)
            return null;
        if (!str.endsWith(ch))
            return str + ch;
        else
            return str;
    }

    public static String replaceStr(String strSrc, String strOld, String strNew) {
        if (strSrc == null)
            return null;
        char srcBuff[] = strSrc.toCharArray();
        int nSrcLen = srcBuff.length;
        if (nSrcLen == 0)
            return "";
        char oldStrBuff[] = strOld.toCharArray();
        int nOldStrLen = oldStrBuff.length;
        if (nOldStrLen == 0 || nOldStrLen > nSrcLen)
            return strSrc;
        StringBuffer retBuff = new StringBuffer(nSrcLen * (1 + strNew.length() / nOldStrLen));
        boolean bIsFound = false;
        for (int i = 0; i < nSrcLen;) {
            bIsFound = false;
            if (srcBuff[i] == oldStrBuff[0]) {
                int j;
                for (j = 1; j < nOldStrLen && i + j < nSrcLen && srcBuff[i + j] == oldStrBuff[j]; j++)
                    ;
                bIsFound = (j == nOldStrLen);
            }
            if (bIsFound) {
                retBuff.append(strNew);
                i += nOldStrLen;
            }
            else {
                int nSkipTo;
                if (i + nOldStrLen >= nSrcLen)
                    nSkipTo = nSrcLen - 1;
                else
                    nSkipTo = i;
                while (i <= nSkipTo) {
                    retBuff.append(srcBuff[i]);
                    i++;
                }
            }
        }

        srcBuff = null;
        oldStrBuff = null;
        return retBuff.toString();
    }

    public static String replaceStr(StringBuffer strSrc, String strOld, String strNew) {
        if (strSrc == null)
            return null;
        int nSrcLen = strSrc.length();
        if (nSrcLen == 0)
            return "";
        char oldStrBuff[] = strOld.toCharArray();
        int nOldStrLen = oldStrBuff.length;
        if (nOldStrLen == 0 || nOldStrLen > nSrcLen)
            return strSrc.toString();
        StringBuffer retBuff = new StringBuffer(nSrcLen * (1 + strNew.length() / nOldStrLen));
        boolean bIsFound = false;
        for (int i = 0; i < nSrcLen;) {
            bIsFound = false;
            if (strSrc.charAt(i) == oldStrBuff[0]) {
                int j;
                for (j = 1; j < nOldStrLen && i + j < nSrcLen && strSrc.charAt(i + j) == oldStrBuff[j]; j++)
                    ;
                bIsFound = j == nOldStrLen;
            }
            if (bIsFound) {
                retBuff.append(strNew);
                i += nOldStrLen;
            }
            else {
                int nSkipTo;
                if (i + nOldStrLen >= nSrcLen)
                    nSkipTo = nSrcLen - 1;
                else
                    nSkipTo = i;
                while (i <= nSkipTo) {
                    retBuff.append(strSrc.charAt(i));
                    i++;
                }
            }
        }

        oldStrBuff = null;
        return retBuff.toString();
    }

    public static String byteToString(byte bytes[], char ch, int radix) {
        String sRet = "";
        for (int i = 0; i < bytes.length; i++) {
            if (i > 0)
                sRet = String.valueOf(String.valueOf(sRet)).concat(",");
            sRet = String.valueOf(sRet) + String.valueOf(Integer.toString(bytes[i], radix));
        }

        return sRet;
    }

    /** 截取字符串 * */
    public static String truncateStr(String _string, int _maxLength) {
        return truncateStr(_string, _maxLength, "..");
    }

    public static String truncateStr(String _string, int _maxLength, String _sExt) {
        if (_string == null)
            return null;
        String sExt = "..";
        if (_sExt != null)
            sExt = _sExt;
        int nExtLen = getBytesLength(sExt);
        if (nExtLen >= _maxLength)
            return _string;
        int nMaxLen = (_maxLength - nExtLen) + 1;
        char srcBuff[] = _string.toCharArray();
        int nLen = srcBuff.length;
        StringBuffer dstBuff = new StringBuffer(nLen + 2);
        int nGet = 0;
        int i = 0;
        do {
            if (i >= nLen)
                break;
            char aChar = srcBuff[i];
            boolean bUnicode = false;
            int j = 0;
            if (aChar == '&') {
                for (j = i + 1; j < nLen && j < i + 9 && !bUnicode; j++) {
                    char cTemp = srcBuff[j];
                    if (cTemp != ';')
                        continue;
                    if (j == i + 5) {
                        bUnicode = false;
                        j = 0;
                        break;
                    }
                    bUnicode = true;
                }

                nGet++;
            }
            else {
                nGet += aChar > '\177' ? 2 : 1;
            }
            if (nGet >= nMaxLen) {
                if (nGet == _maxLength && i == nLen - 1) {
                    dstBuff.append(aChar);
                    for (; i < j - 1; i++)
                        dstBuff.append(srcBuff[i + 1]);
                }
                else {
                    dstBuff.append(sExt);
                }
                break;
            }
            dstBuff.append(aChar);
            for (; i < j - 1; i++)
                dstBuff.append(srcBuff[i + 1]);

            i++;
        } while (true);
        return dstBuff.toString();
    }

    public static int getBytesLength(String str) {
        if (str == null)
            return 0;
        char srcBuff[] = str.toCharArray();
        int nGet = 0;
        for (int i = 0; i < srcBuff.length; i++) {
            char aChar = srcBuff[i];
            nGet += aChar > '\177' ? 2 : 1;
        }

        return nGet;
    }

    public static String[] split(String str, String regx) throws Exception {
        if (isEmpty(str))
            return null;
        return str.split(regx);
    }

    public static Long[] splitLong(String str, String regx) throws Exception {
        String result[] = split(str, regx);
        if (result != null) {
            Long[] res = new Long[result.length];
            int i = 0;
            for (String s : result) {
                res[i++] = Long.parseLong(s);
            }
            return res;
        }
        return null;
    }

    public static String transDisplay(String content) {
        return transDisplay(content, true);
    }

    public static String transDisplay(String content, boolean changeBlank) {
        if (content == null)
            return "";
        char srcBuff[] = content.toCharArray();
        int nSrcLen = srcBuff.length;
        StringBuffer retBuff = new StringBuffer(nSrcLen * 2);
        for (int i = 0; i < nSrcLen; i++) {
            char cTemp = srcBuff[i];
            switch (cTemp) {
                case 32: // ' '
                    retBuff.append(changeBlank ? "&nbsp;" : " ");
                    break;
                case 60: // '<'
                    retBuff.append("&lt;");
                    break;
                case 62: // '>'
                    retBuff.append("&gt;");
                    break;
                case 10: // '\n'
                    retBuff.append("<br>");
                    break;
                case 34: // '"'
                    retBuff.append("&quot;");
                    break;
                case 38: // '&'
                    boolean bUnicode = false;
                    for (int j = i + 1; j < nSrcLen && !bUnicode; j++) {
                        cTemp = srcBuff[j];
                        if (cTemp == '#' || cTemp == ';') {
                            retBuff.append("&");
                            bUnicode = true;
                        }
                    }

                    if (!bUnicode)
                        retBuff.append("&amp;");
                    break;
                case 9: // '\t'
                    retBuff.append(changeBlank ? "&nbsp;&nbsp;&nbsp;&nbsp;" : "    ");
                    break;
                default:
                    retBuff.append(cTemp);
                    break;
            }
        }

        return retBuff.toString();
    }

    public static boolean containsIgnoreCase(String str1, String str2) {
        if (str1 == null || str2 == null)
            return false;
        if (str1.equalsIgnoreCase(str2))
            return true;
        return false;
    }

    /**
     * 判断是否是中文
     */
    public static boolean hasFullChar(String str) {
        if (str.getBytes().length == str.length()) {
            return false;
        }
        return true;
    }

    public static Collection<String> array2List(String array[]) {
        Collection<String> coll = new ArrayList<String>();
        for (int i = 0; i < array.length; i++) {
            if (!isEmpty(array[i]))
                coll.add(array[i]);
        }
        return coll;
    }

    /**
     * 删除input字符串中的html格式
     * 
     * @param input
     * @param length
     * @return
     */
    public static String splitAndFilterString(String input) {
        if (input == null || input.trim().equals("")) {
            return "";
        }
        // 去掉所有html元素,
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");
        return str;
    }

    public static String getStrbyarray(String[] array) {
        if (array == null || array.length == 0)
            return null;
        StringBuffer str = new StringBuffer("");
        for (String s : array) {
            str.append(s);
            str.append(",");
        }
        return str.toString().substring(0, str.toString().length() - 1);
    }

    public static String appendChar$str(String str, char endwith) {
        if (isEmpty(str))
            return "";
        if (str.charAt(str.length() - 1) != endwith) {
            str += endwith;
        }
        return str;
    }

    /**
     * 数据转为集合
     * 
     * @param classtype 转化后的类型
     * 
     */
    public static Collection<Object> arr2collect(String array[], String classtype) {
        Collection<Object> coll = new ArrayList<Object>();
        for (int i = 0; i < array.length; i++) {
            if (!isEmpty(array[i])) {
                if ("long".equalsIgnoreCase(classtype)) {
                    coll.add(new Long(array[i]));
                }
                else if ("string".equalsIgnoreCase(classtype)) {
                    coll.add((array[i]));
                }
                else if ("integer".equalsIgnoreCase(classtype)) {
                    coll.add(new Integer(array[i]));
                }
            }
        }
        return coll;
    }

    /**
     * 把字符替换成数字，支持浮点
     * 
     */
    public static String repStr2Numberstr(String str) {
        if (str == null)
            return str;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '.' && (!Character.isDigit(str.charAt(i)))) {
                str = str.replaceAll(str.charAt(i) + "", "");
            }
        }
        return str;
    }

    /**
     * 去掉重复的
     */
    public static String removeDuplicate(String tomail) {
        if (StringUtils.isEmpty(tomail))
            return "";
        Set<String> set = new HashSet<String>(0);
        String _$mails[] = tomail.split(",");
        for (String s : _$mails) {
            set.add(s);
        }
        StringBuffer returnstr = new StringBuffer("");
        for (String s : set) {
            returnstr.append(s + ",");
        }
        return returnstr.toString();
    }
}
