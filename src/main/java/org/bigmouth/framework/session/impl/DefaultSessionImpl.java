/*
 * 文件名称: DefaultSessionImpl.java
 * 版权信息: Copyright 2005-2013 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2013-3-4
 * 修改内容: 
 */
package org.bigmouth.framework.session.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.bigmouth.framework.session.Session;
import org.bigmouth.framework.session.SessionFactory;
import org.bigmouth.framework.session.Trackable;

/**
 * Session会话的接口默认实现。
 * 
 * @author Allen.Hu / 2013-3-4
 */
public class DefaultSessionImpl implements Session, Serializable {

    private static final long serialVersionUID = 6909663503143540434L;

    private final String id;

    private volatile transient SessionFactory factory;

    private volatile long lastActiveTime;

    private volatile boolean isNew = true;

    private volatile ConcurrentMap<String, Object> attributes = new ConcurrentHashMap<String, Object>();

    public DefaultSessionImpl(String id, SessionFactory factory) {
        super();
        this.id = id;
        this.factory = factory;
        this.lastActiveTime = new Date().getTime();
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.skymobi.pcsuit.commons.session.Session#getId()
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.skymobi.pcsuit.commons.session.Session#setAttribute(java.lang.String, java.lang.Object)
     */
    @Override
    public void setAttribute(String key, Object value) {
        if (StringUtils.isBlank(key))
            throw new IllegalArgumentException("key is blank.");
        if (value == null)
            throw new NullPointerException("value is null.");
        attributes.put(key, value);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.skymobi.pcsuit.commons.session.Session#getAttribute(java.lang.String)
     */
    @Override
    public Object getAttribute(String key) {
        if (StringUtils.isBlank(key))
            throw new IllegalArgumentException("key is blank.");
        return attributes.get(key);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.skymobi.pcsuit.commons.session.Session#exist(java.lang.String)
     */
    @Override
    public boolean exist(String key) {
        if (StringUtils.isBlank(key))
            throw new IllegalArgumentException("key is blank.");
        return attributes.containsKey(key);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.skymobi.pcsuit.commons.session.Session#getLastActiveTime()
     */
    @Override
    public long getLastActiveTime() {
        return lastActiveTime;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.skymobi.pcsuit.commons.session.Session#isNew()
     */
    @Override
    public boolean isNew() {
        return isNew;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.skymobi.pcsuit.commons.session.Session#update(long)
     */
    @Override
    public void update(long time) {
        this.lastActiveTime = time;
        this.isNew = false;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.skymobi.pcsuit.commons.session.Session#setFactory(com.skymobi.pcsuit.commons.session.SessionFactory)
     */
    @Override
    public void setFactory(SessionFactory factory) {
        if (factory == null)
            throw new NullPointerException("SessionFactory is null.");
        this.factory = factory;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.skymobi.pcsuit.commons.session.Session#destroy()
     */
    @Override
    public void destroy() {
        this.factory.remove(new Trackable() {

            @Override
            public String getTrackId() {
                return getId();
            }
            
        });
    }

    public SessionFactory getFactory() {
        return factory;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("attrs", attributes)
        .append("lastActiveTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(lastActiveTime))).toString();
    }
}
