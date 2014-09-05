/*
 * 文件名称: SftpFTP.java
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


/**
 * 
 * @author Allen.Hu / 2014-4-2
 */
public class SshFTPClient extends AbstractFTPClient implements FTP {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SshFTPClient.class);
    
    private int timeout = 10000;
    
    private Session session = null;
    
    private ChannelSftp channel = null;

    public SshFTPClient(String hostname, int port, String username, String password) {
        super(hostname, port, username, password);
    }

    public SshFTPClient(String hostname, int port) {
        super(hostname, port);
    }

    public SshFTPClient(String hostname) {
        super(hostname);
    }

    public void connect() throws IOException {
        try {
            if (null == channel || channel.isClosed()) {
                LOGGER.debug("Beginning to connect the SSH FTP server:" + hostname);
                JSch jsch = new JSch();
                session = jsch.getSession(username, hostname, port);
                if (StringUtils.isNotBlank(password)) {
                    session.setPassword(password);
                }
                Properties config = new Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);
                session.setTimeout(timeout);
                LOGGER.debug("Verifying authorize...");
                session.connect();
    
                channel = (ChannelSftp) session.openChannel("sftp");
                channel.connect();
                LOGGER.debug("Connect successful.");
            }
        }
        catch (JSchException e) {
            throw new IOException(e);
        }
    }
    
    @Override
    public void mkdir(String path) throws IOException {
        try {
            connect();
            mkd(path);
        }
        catch (SftpException e) {
            LOGGER.error("mkdir: ", e);
            throw new IOException(e);
        }
        finally {
            if (autoDisconnect)
                disconnect();
        }
    }
    
    private boolean dirExists(String path) throws IOException {
        try {
            channel.cd(path);
            return true;
        }
        catch (SftpException e) {
            return false;
        }
    }
    
    /**
     * 递归创建目录
     * 
     * @param path
     * @throws IOException
     * @throws SftpException 
     */
    private void mkd(String path) throws IOException, SftpException {
        if (!dirExists(path)) {
            String[] dirs = StringUtils.split(path, "/");
            String full = "/";
            for (String dir : dirs) {
                full += dir + "/";
                if (!dirExists(full)) {
                    channel.mkdir(full);
                }
            }
        }
    }

    @Override
    public boolean exists(String path, String fileName) throws IOException {
        try {
            connect();
            Vector<?> ls = channel.ls(path);
            for (Object object : ls) {
                if (object instanceof LsEntry) {
                    if (StringUtils.equals(((LsEntry)object).getFilename(), fileName))
                        return true;
                }
            }
            return false;
        }
        catch (Exception e) {
            LOGGER.error("exists: ", e);
            throw new IOException(e);
        }
        finally {
            if (autoDisconnect)
                disconnect();
        }
    }

    @Override
    public void upload(String path, File file, String fileName) throws IOException {
        OutputStream outstream = null;
        InputStream instream = null;
        try {
            int c = 0;
            Exception ex = null;
            while (true) {
                if (c == RETRY_NUM) {
                    throw ex;
                }
                try {
                    if (file.exists()) {
                        connect();
                        String realName = (StringUtils.isBlank(fileName) ? file.getName() : fileName);
                        String remote = path + realName;
                        mkdir(path);
                        channel.cd(path);
                        LOGGER.debug("Starting upload files to " + remote);
                        outstream = channel.put(realName);
                        instream = new FileInputStream(file);
                        byte b[] = new byte[1024];
                        int n;
                        while ((n = instream.read(b)) != -1) {
                            outstream.write(b, 0, n);
                        }
                        outstream.flush();
                        LOGGER.info("File upload successful.");
                        break;
                    }
                    else {
                        throw new IOException("Local file " + file.getPath() + " does not exist.");
                    }
                }
                catch (Exception e) {
                    LOGGER.error("upload: " + e.getMessage());
                    ex = e;
                }
                finally {
                    c++;
                }
            }
        }
        catch (Exception e) {
            throw new IOException(e);
        }
        finally {
            IOUtils.closeQuietly(instream);
            IOUtils.closeQuietly(outstream);
            if (autoDisconnect)
                disconnect();
        }
    }

    @Override
    public void test() throws IOException {
        try {
            connect();
        }
        finally {
            if (autoDisconnect)
                disconnect();
        }
    }

    @Override
    public void disconnect() {
        if (null != channel)
            channel.disconnect();
        if (null != session)
            session.disconnect();
        LOGGER.info("Connection has closed.");
    }

    public static void main(String[] args) {
        FTP ftp = new SshFTPClient("124.160.11.211", 8022, "user1", "qwerty!234");
        try {
            ftp.setAutoDisconnect(false);
            String path = "/user1/allen.hu/jvector/maps/china/";
            ftp.upload(path, new File("C:\\Users\\allen.hu\\Desktop\\Struts2.0中文教程.chm"), "Struts2.0中文教程.chm");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            ftp.disconnect();
        }
    }
}
