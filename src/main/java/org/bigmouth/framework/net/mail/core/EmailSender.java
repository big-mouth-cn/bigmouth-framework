/*
 * 文件名称: EmailSender.java
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


/**
 * 邮件发送者接口，具体实现者需要实现该接口。
 * 
 * @author allen.hu / 2012-9-6
 */
public interface EmailSender {

    /**
     * 发送邮件接口
     * 
     * @param message
     * @return
     */
    boolean send(Email message) ;
}
