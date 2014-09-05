/*
 * 文件名称: MultiDataSource1.java
 * 版权信息: Copyright 2005-2012 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2012-7-2
 * 修改内容: 
 */
package org.bigmouth.framework.dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 多数据源。实现了DataSource，ApplicationContextAware接口。
 * 
 * @author Allen.Hu / 2012-7-2
 * @since SkyMarket 1.0
 */
public class MultiDataSource extends BasicDataSource implements ApplicationContextAware {

    /** Spring 上下文 */
    private ApplicationContext applicationContext = null;

    /** 默认数据源 */
    private DataSource defaultDataSource = null;

    /** 日志记录器 */
    private static Logger logger = Logger.getLogger(MultiDataSource.class);

    /**
     * 获取指定的数据源名称对应的数据源. 
     * 先以指定的数据源名称获取Spring上下文数据源, 如果找不到,
     * 对dataSourceName映射后再次尝试, 仍然找不到, 返回
     * 默认数据源（由Spring容器注入，请查看ApplicationContext.xml的数据源配置信息）。
     * 
     * @param dataSourceName 数据源名称(即Spring Bean ID)
     * @return
     * @author Allen.Hu / 2012-7-2 
     * @since SkyMarket 1.0
     */
    public DataSource getDataSource(String dataSourceName) {
        if (StringUtils.isBlank(dataSourceName)) {
            return this.defaultDataSource;
        }

        // 先以指定的数据源名称获取Spring上下文数据源
        DataSource regDs = getRegisterDataSource(dataSourceName);
        if (regDs != null) {
            return regDs;
        }

        // 如果找不到, 对dataSourceName映射后再次尝试
        regDs = getRegisterDataSource(dataSourceName + "_dataSource");
        if (regDs != null) {
            return regDs;
        }

        // 仍然找不到, 返回默认数据源
        return this.defaultDataSource;
    }

    /**
     * 获取Spring上下文已注册的数据源
     * 
     * @param dataSourceName 数据源名称(即Spring Bean ID)
     * @return
     * @author Allen.Hu / 2012-7-2 
     * @since SkyMarket 1.0
     */
    private DataSource getRegisterDataSource(String dataSourceName) {
        try {
            return (DataSource) this.applicationContext.getBean(dataSourceName);
        }
        catch (NoSuchBeanDefinitionException ex) {
            logger.warn("There is not the dataSource <name:" + dataSourceName + "> in the applicationContext!");
        }

        return null;
    }

    /**
     * 获取数据源.
     * 
     * @return
     * @author Allen.Hu / 2012-7-2 
     * @since SkyMarket 1.0
     */
    public DataSource getDataSource() {
        String sp = SpObserver.getDsFlag();
        return getDataSource(sp);
    }

    // -------------------------------- 以下为Getter/Setter方法 -------------------------------- //

    public void setDefaultDataSource(DataSource defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

    public DataSource getDefaultDataSource() {
        return defaultDataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getDataSource().getConnection(username, password);
    }

    /**
     * 获取日志输出源
     * 
     * @return 日志输出源
     * @see javax.sql.CommonDataSource#getLogWriter()
     */
    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return getDataSource().getLogWriter();
    }

    /**
     * 设置日志输出源
     * 
     * @param out 日志输出源
     * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
     */
    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        getDataSource().setLogWriter(out);
    }

    /**
     * 获取登录超时时间
     * 
     * @return 登录超时时间
     * @see javax.sql.CommonDataSource#getLoginTimeout()
     */
    @Override
    public int getLoginTimeout() throws SQLException {
        return getDataSource().getLoginTimeout();
    }

    /**
     * 设置登录超时时间
     * 
     * @param seconds 登录超时时间
     * @see javax.sql.CommonDataSource#setLoginTimeout(int)
     */
    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        getDataSource().setLoginTimeout(seconds);
    }

    /**
     * 注入Spring 上下文
     * 
     * @param applicationContext Spring 上下文
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
