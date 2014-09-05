/*
 * 文件名称: ShanxiContextLoaderListener.java
 * 版权信息: Copyright 2012 Bigmouth-Framework. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2012-7-5
 * 修改内容: 
 */
package org.bigmouth.framework.core;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;

/**
 * 上下文加载监听器, 从Spring继承而来. 该类的作用可初始化一些参数.
 * 
 * @author Allen.Hu / 2012-7-5
 * @since Bigmouth-Framework 1.0
 */
public class FrameworkContextLoaderListener extends ContextLoaderListener {

    private static Logger logger = Logger.getLogger(FrameworkContextLoaderListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        String webRootRealPath = event.getServletContext().getRealPath("/");

        logger.debug("===============================================");
        logger.debug("开始加载上下文...");
        logger.debug("===============================================");

        logger.info("工程所在路径：" + webRootRealPath);
        super.contextInitialized(event);

        logger.debug("===============================================");
        logger.debug("加载上下文成功...");
        logger.debug("===============================================");

    }
}
