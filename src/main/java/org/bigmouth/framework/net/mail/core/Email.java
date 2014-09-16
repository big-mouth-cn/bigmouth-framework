/*
 * 文件名称: Email.java
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Email 消息内容、邮件头定义。
 * 
 * @author allen.hu / 2012-9-6
 */
public class Email implements Serializable, Cloneable {

    private static final long serialVersionUID = -7688424681336213133L;

    /** 普通的邮件 */
    public static final int SIMPLE_EMAIL = 0;

    /** 带附件的邮件 */
    public static final int MULTI_PART_EMAIL = 1;

    /** HTML邮件 */
    public static final int HTML_EMAIL = 2;

    /** 邮件主题 */
    private String subject;

    /** 邮件发件人地址 */
    private String from;

    /** 邮件发件人名称 */
    private String display;

    /** 收件人地址列表 */
    private List<String> to;

    /** 邮件编码: 默认GBK */
    private String charset = "GBK";

    /** 邮件正文 */
    private String content;

    /** 附件列表 */
    private List<String> attachments;

    /** Email类型 0: 简单邮件 1:带附件邮件(默认) 2: HTML格式邮件 */
    private int emailType = MULTI_PART_EMAIL;
    
    /** 默认构造函数 */
    public Email() {

    }

    public Email(String subject, String content) {
        super();
        this.subject = subject;
        this.content = content;
    }

    public Email(String subject, List<String> to, String content) {
        super();
        this.subject = subject;
        this.to = to;
        this.content = content;
    }

    public Email(String subject, List<String> to, String content, int emailType) {
        super();
        this.subject = subject;
        this.to = to;
        this.content = content;
        this.emailType = emailType;
    }

    public Email(String subject, String from, String display, List<String> to, String charset, String content,
            List<String> attachments, int emailType) {
        super();
        this.subject = subject;
        this.from = from;
        this.display = display;
        this.to = to;
        this.charset = charset;
        this.content = content;
        this.attachments = attachments;
        this.emailType = emailType;
    }

    /**
     * 添加一个收件人邮箱地址。
     * 
     * @param to
     */
    public void addTo(String to) {
        if (null == this.to)
            this.to = new ArrayList<String>();
        this.to.add(to);
    }

    /**
     * 添加一个附件。
     * 
     * @param attachment
     */
    public void addAttachment(String attachment) {
        if (attachments == null) {
            attachments = new ArrayList<String>();
        }
        attachments.add(attachment);
    }

    @Override
    public Object clone() {

        Email cloneEmail = null;
        try {
            cloneEmail = (Email) super.clone();

            // 不可变的字符串和Java基本类型, 可以直接赋值进行Clone
            cloneEmail.subject = this.subject;
            cloneEmail.charset = this.charset;
            cloneEmail.emailType = this.emailType;
            cloneEmail.content = this.content;
            cloneEmail.from = this.from;
            cloneEmail.display = this.display;

            // 集合类型必须逐项进行Clone
            if (this.to != null) {
                cloneEmail.to = new ArrayList<String>();
                for (String str : this.to) {
                    cloneEmail.to.add(str);
                }
            }

            if (this.attachments != null) {
                cloneEmail.attachments = new ArrayList<String>();
                for (String str : this.attachments) {
                    cloneEmail.attachments.add(str);
                }
            }
        }
        catch (CloneNotSupportedException e) {
        }

        return cloneEmail;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("EmailMessage{").append(" subject=").append(subject).append(", from=").append(from).append(
                ", display=").append(display).append(", to=").append(to).append(", charset=").append(charset).append(
                ", content=").append(content).append(", attachments=").append(attachments).append(", emailType=")
                .append(emailType).append(" }");

        return sb.toString();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
    
    public void setFrom(String from, String display) {
        this.from = from;
        this.display = display;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public int getEmailType() {
        return emailType;
    }

    public void setEmailType(int emailType) {
        this.emailType = emailType;
    }
}
