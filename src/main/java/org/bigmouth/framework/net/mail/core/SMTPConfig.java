/*
 * 文件名称: SMTPConfig.java
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
 * SMTP 配置对象。
 * 
 * @author allen.hu / 2012-9-6
 */
public class SMTPConfig {

    /** serialVersionUID */
    private static final long serialVersionUID = 8615325201966868745L;

    /** 默认SMTP服务器端口 */
    public static final int DEFAULT_SMTP_PORT = 25;

    /** SMTP服务器 */
    private String hostName;

    /** SMTP服务端口: 默认25 */
    private int smtpPort = DEFAULT_SMTP_PORT;

    /** SMTP服务器验证用户名 */
    private String username;

    /** SMTP服务器验证密码 */
    private String password;
    
    private boolean ssl = false;

    /** 默认构造函数 */
    public SMTPConfig() {

    }

    /**
     * 构造函数。
     * 
     * @param hostName SMTP服务器
     * @param username SMTP服务器验证用户名
     * @param password SMTP服务器验证密码
     */
    public SMTPConfig(String hostName, String username, String password) {
        this(hostName, DEFAULT_SMTP_PORT, username, password);
    }

    /**
     * 构造函数。
     * 
     * @param hostName SMTP服务器
     * @param smtpPort SMTP服务端口
     * @param username SMTP服务器验证用户名
     * @param password SMTP服务器验证密码
     */
    public SMTPConfig(String hostName, int smtpPort, String username, String password) {
        this(hostName, smtpPort, username, password, false);
    }

    public SMTPConfig(String hostName, int smtpPort, String username, String password, boolean ssl) {
        this.hostName = hostName;
        this.smtpPort = smtpPort;
        this.username = username;
        this.password = password;
        this.ssl = ssl;
    }

    /**
     * toString Method
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("SmtpInfo{").append(" hostName=").append(hostName).append(", smtpPort=").append(smtpPort).append(
                ", username=").append(username).append(", password=").append(password).append(" }");

        return sb.toString();
    }

    // -------------------------------- 以下为Getter/Setter方法 -------------------------------- //

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
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

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }
}
