package org.bigmouth.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.bigmouth.framework.lang.FrameworkException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

/**
 * Properties Util函数. 加载属性文件
 * 
 */
public class PropertiesUtils {

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static Logger logger = Logger.getLogger(PropertiesUtils.class);

    private static PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();

    private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    /**
     * 载入多个properties文件, 相同的属性最后载入的文件将会覆盖之前的载入.
     * */
    public static Properties loadProperties(String... locations) {
        Properties props = new Properties();

        for (String location : locations) {
            logger.debug("正在从classpath目录中加载属性文件:" + location);
            InputStream is = null;
            try {
                Resource resource = resourceLoader.getResource(location);
                is = resource.getInputStream();
                propertiesPersister.load(props, new InputStreamReader(is, DEFAULT_ENCODING));
            }
            catch (IOException ex) {
                throw new FrameworkException("属性文件加载失败");
            }
            finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                }
                catch (Exception e) {
                }

            }
        }
        return props;
    }

}
