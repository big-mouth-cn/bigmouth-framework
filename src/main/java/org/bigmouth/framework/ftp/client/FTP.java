/*
 * 文件名称: FTPClient.java
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

import java.io.File;
import java.io.IOException;


/**
 * FTP 客户端操作工具
 * 
 * @author Allen.Hu / 2014-4-2
 */
public interface FTP {

    /**
     * 创建一个目录，如果目标目录已经存在则不会对其有任何改动
     * 
     * @param path
     * @throws IOException
     */
    void mkdir(String path) throws IOException;
    
    /**
     * 指定目录下文件是否存在
     * 
     * @param path
     * @param fileName
     * @return
     * @throws IOException
     */
    @Deprecated
    boolean exists(String path, String fileName) throws IOException;
    
    /**
     * 上传文件到指定目录下。
     * 
     * @param path 目标目录，将文件保存在此目录下
     * @param file 上传的文件
     * @param fileName 目标文件名称，如果为<code>null</code>的话，那么则与本地上传的文件名称保持一致。
     * @throws IOException
     */
    void upload(String path, File file, String fileName) throws IOException;
    
    /**
     * 测试是否可正常访问
     */
    void test() throws IOException;
    
    /**
     * 断开连接
     */
    void disconnect();
    
    /**
     * 设置是否自动关闭连接
     * 
     * @param autoDisconnect
     */
    void setAutoDisconnect(boolean autoDisconnect);
}
