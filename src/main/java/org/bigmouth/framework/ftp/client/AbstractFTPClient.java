/*
 * 文件名称: AbstractFtp.java
 * 版权信息: Copyright 2013-2014 By Allen Hu. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2014-4-2
 * 修改内容: 
 */
package org.bigmouth.framework.ftp.client;

/**
 * 抽象的FTP客户端。
 * 
 * @author Allen.Hu / 2014-4-2
 */
public abstract class AbstractFTPClient {

    public static final int DEFAULT_PORT = 21;

    public static final String DEFAULT_CONTROL_ENCODING = "GBK";
    
    protected static final int RETRY_NUM = 3;

    protected String hostname;

    protected int port = DEFAULT_PORT;

    protected String username;

    protected String password;

    protected boolean autoDisconnect = true;

    protected String controlEncoding = DEFAULT_CONTROL_ENCODING;

    public AbstractFTPClient(String hostname) {
        this.hostname = hostname;
    }

    public AbstractFTPClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public AbstractFTPClient(String hostname, int port, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
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

    public boolean isAutoDisconnect() {
        return autoDisconnect;
    }

    public void setAutoDisconnect(boolean autoDisconnect) {
        this.autoDisconnect = autoDisconnect;
    }

    public String getControlEncoding() {
        return controlEncoding;
    }

    public void setControlEncoding(String controlEncoding) {
        this.controlEncoding = controlEncoding;
    }

}
