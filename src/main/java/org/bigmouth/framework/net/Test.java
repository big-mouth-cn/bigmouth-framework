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
        SMTPConfig config = new SMTPConfig("pop.sky-mobi.com", 465, "allen.hu@sky-mobi.com", "lvlu1314.");
        Email message = new Email();
        message.setEmailType(Email.MULTI_PART_EMAIL);
        message.setSubject("Welcome to HangZhou.");
        message.setFrom("allen.hu@sky-mobi.com", "安伦·胡");
        message.addTo("huxiao.mail@qq.com");
        message.addAttachment("http://www.baidu.com/img/baidu_jgylogo3.gif");
        message.addAttachment("D:\\Workspace\\CDS-Framework\\libs\\commons\\commons-email-current.jar");
        message.setContent("Hello, Allen Welcome to HangZhou.");
        EmailSender sender = EmailSenderFactory.getSender(Email.MULTI_PART_EMAIL, config);
        sender.send(message);
    }
}
