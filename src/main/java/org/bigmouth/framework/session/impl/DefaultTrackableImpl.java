/*
 * 文件名称: DefaultTrackableImpl.java
 * 版权信息: Copyright 2005-2013 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2013-3-5
 * 修改内容: 
 */
package org.bigmouth.framework.session.impl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.bigmouth.framework.session.Trackable;

/**
 * 默认的会话追踪接口实现类。
 * 
 * @author Allen.Hu / 2013-3-5
 */
public class DefaultTrackableImpl implements Trackable {

    private volatile String trackId;

    public DefaultTrackableImpl(String trackId) {
        super();
        this.trackId = trackId;
    }

    public DefaultTrackableImpl(Object object, String attrName) {
        super();
        if (object == null)
            throw new NullPointerException("object is null.");
        if (StringUtils.isBlank(attrName))
            throw new IllegalArgumentException("attrName is blank.");
        try {
            trackId = BeanUtils.getProperty(object, attrName);
        }
        catch (IllegalAccessException e) {
            trackId = null;
        }
        catch (InvocationTargetException e) {
            trackId = null;
        }
        catch (NoSuchMethodException e) {
            trackId = null;
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.skymobi.pcsuit.commons.session.Trackable#getTrackId()
     */
    @Override
    public String getTrackId() {
        return trackId;
    }

}
