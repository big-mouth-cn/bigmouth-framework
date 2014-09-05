/*
 * 文件名称: ShanxiException.java
 * 版权信息: Copyright 2012 Big-mouth framework All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2012-7-4
 * 修改内容: 
 */
package org.bigmouth.framework.lang;


/**
 * 斯凯 异常基类。
 * 
 * @author Allen.Hu / 2012-7-4
 * @since Bigmouth-Framework 1.0
 */
public class FrameworkException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = 5915736340752245590L;

    /**
     * 默认构造函数
     */
    public FrameworkException() {
    }

    /**
     * 构造函数
     * @param message 异常描述
     */
    public FrameworkException(String message) {
        super(message);
    }

    /**
     * 构造函数
     * @param cause 异常信息
     */
    public FrameworkException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     * @param message 异常描述
     * @param cause 异常信息
     */
    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
