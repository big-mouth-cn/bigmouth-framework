/*
 * 文件名称: ShanxiActionException.java
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
 * 斯凯 服务端Action层异常对象。
 * 
 * @author Allen.Hu / 2012-7-4
 * @since Bigmouth-Framework 1.0
 */
public class FrameworkActionException extends FrameworkException {

    /** serialVersionUID */
    private static final long serialVersionUID = 3462191428398331747L;

    /**
     * 默认构造函数
     */
    public FrameworkActionException() {
    }

    /**
     * 构造函数
     * @param message 异常描述
     */
    public FrameworkActionException(String message) {
        super(message);
    }

    /**
     * 构造函数
     * @param cause 异常信息
     */
    public FrameworkActionException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     * @param message 异常描述
     * @param cause 异常信息
     */
    public FrameworkActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
