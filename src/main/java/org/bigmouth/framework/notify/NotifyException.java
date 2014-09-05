/*
 * 文件名称: NotifyException.java
 * 版权信息: Copyright 2012 Huxiao. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Huxiao
 * 修改日期: 2012-3-26
 * 修改内容: 
 */
package org.bigmouth.framework.notify;

/**
 * NotifyException.
 * @author Huxiao created on 2012-3-26
 * @since Observer Framework 1.0
 */
public class NotifyException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = 3933584864102534654L;

    /** 默认构造函数. */
    public NotifyException() {
    }

    /**
     * 构造函数.
     * 
     * @param message 异常信息描述
     */
    public NotifyException(String message) {
        super(message);
    }

    /**
     * 构造函数.
     * 
     * @param cause 异常原因
     */
    public NotifyException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数.
     * 
     * @param message 异常信息描述
     * @param cause 异常原因
     */
    public NotifyException(String message, Throwable cause) {
        super(message, cause);
    }
}
