/*
 * 文件名称: ErrorCode.java
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
 * 错误消息定义。
 * @author allen.hu / 2012-9-6
 */
public interface ErrorCode {

    String UNSUPPORT_EMAIL_TYPE = "不支持的邮件类型";
    
    String MAIL_IS_NULL = "邮件消息对象不能为空";
    
    String MAIL_FROM_ADDR_EMPTY = "发件人地址不能为空";
    
    String MAIL_TO_ADDR_EMPTY = "收件人地址不能为空";
    
    String SMTP_HOST_NULL = "邮件服务器地址不能为空";
    
    String SEND_MAIL_ERROR = "发送邮件失败";
    
    String ADD_URL_ATTACHMENT_ERROR = "添加网络链接类型的邮件附件时发生异常";
    
    String ADD_ATTACHMENT_ERROR = "添加邮件附件时发生异常";
}
