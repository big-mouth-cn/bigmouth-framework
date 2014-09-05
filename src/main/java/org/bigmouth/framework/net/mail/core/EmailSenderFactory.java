/*
 * 文件名称: EmailSenderFactory.java
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

import org.bigmouth.framework.net.mail.sender.HtmlSender;
import org.bigmouth.framework.net.mail.sender.MultiPartSender;

/**
 * Email发送对象工厂类, 根据要发送Email的类型返回具体的发送实现.
 * 
 * @author allen.hu / 2012-9-6
 */
public class EmailSenderFactory {

    /**
     * 获取指定类型的邮件发送者。
     * 
     * @param emailType 类型
     * @param config SMTP配置信息
     * @return
     * @author allen.hu / 2012-9-6 
     */
    public static EmailSender getSender(int emailType, SMTPConfig config) {
        
        EmailSender sender = null;
        
        switch (emailType) {
            case Email.SIMPLE_EMAIL: 
            case Email.HTML_EMAIL: {
                sender = new HtmlSender(config);
                break;
            }
            case Email.MULTI_PART_EMAIL: {
                sender = new MultiPartSender(config);
                break;
            }
            default: {
                throw new EmailException(ErrorCode.UNSUPPORT_EMAIL_TYPE);
            }
        }
        
        return sender;
    }
}
