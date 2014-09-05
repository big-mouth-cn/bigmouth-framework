package org.bigmouth.framework.core;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 * 
 */
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext applicationContext = null;

	private static Logger logger = org.apache.log4j.Logger.getLogger(SpringContextHolder.class);

	/**
	 * 实现ApplicationContextAware接口, 注入Context到静态变量中.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		logger.debug("注入ApplicationContext到SpringContextHolder:" + applicationContext);

		if (SpringContextHolder.applicationContext != null) {
			logger.warn("SpringContextHolder中的ApplicationContext被覆盖,原有Context为:"
					+ SpringContextHolder.applicationContext);
		}

		SpringContextHolder.applicationContext = applicationContext; //NOSONAR
	}

	/**
	 * 实现DisposableBean接口,在Context关闭时清理静态变量.
	 * @Override
	 */
	
	public void destroy() throws Exception {
		SpringContextHolder.cleanApplicationContext();

	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 清除applicationContext静态变量.
	 */
	public static void cleanApplicationContext() {
		logger.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
		applicationContext = null;
	}

	/**
	 * 检查ApplicationContext不为空.
	 */
	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}
	

    /**
     * 从 Spring 上下文获取Bean实例， BeanID 默认为类简单名称或首字母小写的类简单名称。
     * 
     * @param <T> 泛型类型
     * @param beanClass
     * @return Spring Bean 实例
     * @author Allen.Hu / 2012-6-29 
     * @since SkyMarket 1.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> beanClass) {
        Object obj = null;
        String simpleName = beanClass.getSimpleName();
        obj = getBean(simpleName);
        if (null == obj) {
            String beanId = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
            obj = getBean(beanId);
        }
        
        return (T) obj;
    }
    
    /**
     * 获得当前配置的数据源对象。
     * 
     * @return
     * @author Allen.Hu / 2012-6-29 
     * @since SkyMarket 1.0
     */
    public static DataSource getCurrentDataSource() {
        return ((BasicDataSource)getBean("dataSource"));
    }
}
