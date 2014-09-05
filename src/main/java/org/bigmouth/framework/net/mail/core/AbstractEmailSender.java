/*
 * 文件名称: AbstractEmailSender.java
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

import org.apache.commons.lang.StringUtils;
import org.bigmouth.framework.util.CollectionUtils;


/**
 * 抽象的邮件发送者。具体的HTML、带附件的发送者需继承此抽象类。
 * 
 * @author allen.hu / 2012-9-6
 */
public abstract class AbstractEmailSender implements EmailSender {

    protected SMTPConfig config;
    
    protected Email email;

    public AbstractEmailSender(SMTPConfig config) {
        super();
        this.config = config;
    }
    
    public abstract boolean send(Email message) ;
    
    /**
     * 检查邮件信息是否合格
     * 
     * @param email
     */
    protected void check(Email email) {
        if (email == null) {
            throw new EmailException(ErrorCode.MAIL_IS_NULL);
        }
        if (StringUtils.isEmpty(email.getFrom())) {
            throw new EmailException(ErrorCode.MAIL_FROM_ADDR_EMPTY);
        }
        if (CollectionUtils.isEmpty(email.getTo())) {
            throw new EmailException(ErrorCode.MAIL_TO_ADDR_EMPTY);
        }
    }
}
