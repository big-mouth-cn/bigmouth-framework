/*
 * 文件名称: SpringContextProperty.java
 * 版权信息: Copyright 2012 Big-mouth Metro Labs. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2012-11-9
 * 修改内容: 
 */
package org.bigmouth.framework.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


/**
 * <p>
 * <code>
 * 获取Spring上下文中定义的变量值。</br>
 * 在applicationContext.xml中加载properties文件必须使用此类，
 * 否则无法获得指定的变量值。
 * </code>
 * </p>
 * 
 * @author Allen / 2012-9-14
 */
public class SpringContextProperty extends PropertyPlaceholderConfigurer {

    private static Map<String, String> ctxPropertiesMap;

    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException {
        super.processProperties(beanFactory, props);
        ctxPropertiesMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    public static String getContextProperty(String name) {
        return ctxPropertiesMap.get(name);
    }
}
