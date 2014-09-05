/*
 * 文件名称: HtmlSender.java
 * 版权信息: Copyright 2005-2012 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: allen.hu
 * 修改日期: 2012-9-6
 * 修改内容: 
 */
package org.bigmouth.framework.net.mail.sender;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.bigmouth.framework.net.mail.core.AbstractEmailSender;
import org.bigmouth.framework.net.mail.core.Email;
import org.bigmouth.framework.net.mail.core.EmailException;
import org.bigmouth.framework.net.mail.core.ErrorCode;
import org.bigmouth.framework.net.mail.core.SMTPConfig;


/**
 * HTML 格式的邮件发送者。
 * 
 * @author allen.hu / 2012-9-6
 */
public class HtmlSender extends AbstractEmailSender {

    private static org.apache.log4j.Logger LOGGER = Logger.getLogger(HtmlSender.class);

    private HtmlEmail htmlEmail;

    public HtmlSender(SMTPConfig config) {
        super(config);
        htmlEmail = new HtmlEmail();
        initHtmlEmail(config);
    }

    @Override
    public boolean send(Email message) {
        email = message;
        check(email);
        
        try {
            htmlEmail.setFrom(this.email.getFrom(), email.getDisplay());
            for (String to : email.getTo()) {
                htmlEmail.addTo(to);
            }
            htmlEmail.setSubject(email.getSubject());
            htmlEmail.setCharset(email.getCharset());
            htmlEmail.setHtmlMsg(email.getContent());

            // 完成发送
            htmlEmail.send();
        }
        catch (org.apache.commons.mail.EmailException e) {
            LOGGER.error(ErrorCode.SEND_MAIL_ERROR, e);
            throw new EmailException(ErrorCode.SEND_MAIL_ERROR, e);
        }
        return false;
    }

    /**
     * 初始化HTML邮件发送者
     * 
     * @param config
     */
    private void initHtmlEmail(SMTPConfig config) {
        String host = config.getHostName();
        if (StringUtils.isBlank(host)) {
            throw new EmailException(ErrorCode.SMTP_HOST_NULL);
        }

        htmlEmail.setHostName(host);
        htmlEmail.setSmtpPort(config.getSmtpPort());
        htmlEmail.setAuthentication(config.getUsername(), config.getPassword());
    }
}
