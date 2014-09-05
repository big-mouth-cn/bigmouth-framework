/*
 * 文件名称: EmailService.java
 * 版权信息: Copyright 2005-2012 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: allen.hu
 * 修改日期: 2012-9-6
 * 修改内容: 
 */
package org.bigmouth.framework.net.mail;

import org.apache.commons.lang.StringUtils;
import org.bigmouth.framework.net.mail.core.Email;
import org.bigmouth.framework.net.mail.core.EmailException;
import org.bigmouth.framework.net.mail.core.EmailSender;
import org.bigmouth.framework.net.mail.core.EmailSenderFactory;
import org.bigmouth.framework.net.mail.core.ErrorCode;
import org.bigmouth.framework.net.mail.core.SMTPConfig;

/**
 * 邮件发送工具
 * 
 * @author allen.hu / 2012-9-6
 */
public class EmailUtils {

    private String hostName;

    private int port;

    private String username;

    private String password;

    private String display;

    /**
     * 发送邮件
     * @param message
     * @param emailType
     * @author allen.hu / 2012-9-6 
     */
    public void send(Email message, int emailType) {
        if (null == message)
            throw new EmailException(ErrorCode.MAIL_IS_NULL);
        if (StringUtils.isBlank(message.getDisplay()))
            message.setDisplay(display);
        if (StringUtils.isBlank(message.getFrom())) 
            message.setFrom(username);
        message.setEmailType(emailType);
        
        SMTPConfig config = new SMTPConfig(hostName, port, username, password);
        EmailSender sender = EmailSenderFactory.getSender(emailType, config);
        
        sender.send(message);
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

}
