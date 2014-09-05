/*
 * 文件名称: MultiPartSender.java
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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;
import org.bigmouth.framework.net.mail.core.AbstractEmailSender;
import org.bigmouth.framework.net.mail.core.Email;
import org.bigmouth.framework.net.mail.core.EmailException;
import org.bigmouth.framework.net.mail.core.ErrorCode;
import org.bigmouth.framework.net.mail.core.SMTPConfig;
import org.bigmouth.framework.util.CollectionUtils;


/**
 * 带附件的邮件发送者。
 * 
 * @author allen.hu / 2012-9-6
 */
public class MultiPartSender extends AbstractEmailSender {
    
    private static Logger LOGGER = Logger.getLogger(MultiPartSender.class);
    
    private MultiPartEmail multiPartEmail;
    
    public MultiPartSender(SMTPConfig config) {
        super(config);
        multiPartEmail = new MultiPartEmail();
        this.initMultiPartEmail(config);
    }

    @Override
    public boolean send(Email message) {
        this.email = message;
        
        check(this.email);
        
        try {
            multiPartEmail.setFrom(this.email.getFrom(), this.email.getDisplay());
            for (String to : this.email.getTo()) {
                multiPartEmail.addTo(to);
            }
            multiPartEmail.setSubject(email.getSubject());
            multiPartEmail.setCharset(email.getCharset());
            multiPartEmail.setMsg(email.getContent());
            this.attach(email.getAttachments());
            multiPartEmail.send();
        }
        catch (org.apache.commons.mail.EmailException e) {
            LOGGER.error(ErrorCode.SEND_MAIL_ERROR, e);
            throw new EmailException(ErrorCode.SEND_MAIL_ERROR, e);
        }
        return false;
    }
    
    /**
     * 向邮件中添加附件。
     * 
     * @param attachmentFiles
     */
    private void attach(List<String> attachmentFiles) {
        if (CollectionUtils.isNotEmpty(attachmentFiles)) {
            EmailAttachment attach = null;
            
            try {
                for (String file : attachmentFiles) {
                    attach = getEmailAttachment(file);
                    multiPartEmail.attach(attach);
                }
            }
            catch (org.apache.commons.mail.EmailException e) {
                throw new EmailException(ErrorCode.ADD_ATTACHMENT_ERROR, e);
            }
        }
    }
    
    /**
     * 设置附件地址
     * 
     * @param file
     * @return
     */
    private EmailAttachment getEmailAttachment(String file) {
        EmailAttachment attach = new EmailAttachment();
        if (file.indexOf("http") == -1) {
            attach.setPath(file);
        }
        else {
            try {
                attach.setURL(new URL(file));
            }
            catch (MalformedURLException e) {
                throw new EmailException(ErrorCode.ADD_URL_ATTACHMENT_ERROR, e);
            }
        }
        
        return attach;
    }
    
    /**
     * 初始化附件邮件发送者
     * 
     * @param config
     */
    private void initMultiPartEmail(SMTPConfig config) {
        String host = config.getHostName();
        if (StringUtils.isBlank(host)) {
            throw new EmailException(ErrorCode.SMTP_HOST_NULL);
        }
        multiPartEmail.setHostName(host);
        multiPartEmail.setSmtpPort(config.getSmtpPort());
        multiPartEmail.setAuthentication(config.getUsername(), config.getPassword());
    }

}
