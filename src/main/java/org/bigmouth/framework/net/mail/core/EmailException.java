/*
 * 文件名称: EmailException.java
 * 版权信息: Copyright 2005-2012 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: allen.hu
 * 修改日期: 2012-9-6
 * 修改内容: 
 */
package org.bigmouth.framework.net.mail.core;

import org.bigmouth.framework.net.NetException;

/**
 * 邮件异常。
 * 
 * @author allen.hu / 2012-9-6
 */
public class EmailException extends NetException {

    private static final long serialVersionUID = -327458686282245391L;

    public EmailException() {
        super();
    }

    public EmailException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public EmailException(String arg0) {
        super(arg0);
    }

    public EmailException(Throwable arg0) {
        super(arg0);
    }

}
