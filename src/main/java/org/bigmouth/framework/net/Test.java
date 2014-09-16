/*
 * 文件名称: Test.java
 * 版权信息: Copyright 2005-2012 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: allen.hu
 * 修改日期: 2012-9-6
 * 修改内容: 
 */
package org.bigmouth.framework.net;

import org.bigmouth.framework.net.mail.core.Email;
import org.bigmouth.framework.net.mail.core.EmailSender;
import org.bigmouth.framework.net.mail.core.EmailSenderFactory;
import org.bigmouth.framework.net.mail.core.SMTPConfig;


/**
 * 
 * @author allen.hu / 2012-9-6
 */
public class Test {

    public static void main(String[] args) {
        SMTPConfig config = new SMTPConfig("smtp.exmail.qq.com", 465, "master@big-mouth.cn", "lvlu1314.", true);
        Email message = new Email();
        message.setEmailType(Email.HTML_EMAIL);
        message.setSubject("Welcome to HangZhou.");
        message.setFrom("master@big-mouth.cn", "安伦·胡");
        message.addTo("huxiao.mail@qq.com");
        message.setContent("Hello, Allen Welcome to HangZhou.");
        EmailSender sender = EmailSenderFactory.getSender(Email.HTML_EMAIL, config);
        sender.send(message);
    }
}
