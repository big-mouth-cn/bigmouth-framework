package org.bigmouth.framework.ftp.server;

import java.io.File;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.bigmouth.framework.util.BaseLifeCycleSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class FTPServerFactory extends BaseLifeCycleSupport {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FTPServerFactory.class);
    
    private final String host;
    private int port = 21;
    
    private FtpServer server;

    public FTPServerFactory(String host) {
        this.host = host;
    }

    public FTPServerFactory(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    protected abstract File getProperties();

    @Override
    protected void doInit() {
        try {
            FtpServerFactory factory = new FtpServerFactory();
            PropertiesUserManagerFactory managerFactory = new PropertiesUserManagerFactory();
            
            ListenerFactory listenerFactory = new ListenerFactory();
            listenerFactory.setServerAddress(host);
            listenerFactory.setPort(port);
            factory.addListener("default", listenerFactory.createListener());
            
            File properties = getProperties();
            if ( null == properties || !properties.exists() )
                throw new NullPointerException("properties");
            
            managerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());
            managerFactory.setFile(properties);
            factory.setUserManager(managerFactory.createUserManager());
            server = factory.createServer();
            server.start();
            String addr = getAddr();
            LOGGER.info("FTP Server {} has started successfully!", addr);
        }
        catch (Exception e) {
            throw new RuntimeException("FTP Server start failured!", e);
        }
    }

    private String getAddr() {
        return "ftp://" + host + ":" + port;
    }

    @Override
    protected void doDestroy() {
        if (null != server) {
            server.stop();
        }
    }
}
