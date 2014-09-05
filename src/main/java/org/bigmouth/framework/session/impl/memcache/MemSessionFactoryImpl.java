/*
 * 文件名称: MemSessionFactoryImpl.java
 * 版权信息: Copyright 2005-2013 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2013-3-4
 * 修改内容: 
 */
package org.bigmouth.framework.session.impl.memcache;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.lang.StringUtils;
import org.bigmouth.framework.session.Session;
import org.bigmouth.framework.session.SessionFactory;
import org.bigmouth.framework.session.Trackable;
import org.bigmouth.framework.session.impl.DefaultSessionImpl;
import org.bigmouth.framework.session.util.UUIDUtils;

/**
 * 基于MemCached缓存机制的会话工厂实现类。
 * 
 * @author Allen.Hu / 2013-3-4
 */
public class MemSessionFactoryImpl implements SessionFactory {

    private static final int DEFAULT_EXPIRE_TIME = 0;

    /** MemCache缓存 */
    private final MemcachedClient memClient;

    /** 会话过期时间 */
    private final int expireTime;

    public MemSessionFactoryImpl(MemcachedClient memClient) {
        this(memClient, DEFAULT_EXPIRE_TIME);
    }

    public MemSessionFactoryImpl(MemcachedClient memClient, int expireTime) {
        super();
        if (memClient == null)
            throw new NullPointerException("memClient is null.");
        if (expireTime < 0)
            throw new RuntimeException("expireTime : " + expireTime);
        this.memClient = memClient;
        this.expireTime = expireTime;
    }
    
    protected Session createSession() {
        return new DefaultSessionImpl(UUIDUtils.getUUID(), this);
    }
    
    protected Session createSession(String id) {
        return new DefaultSessionImpl(id, this);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.skymobi.pcsuit.commons.session.SessionFactory#getSession(com.skymobi.pcsuit.commons.session.Trackable)
     */
    @Override
    public Session getSession(Trackable trackable) {
        if (trackable == null)
            throw new NullPointerException("trackable is null.");
        String trackId = trackable.getTrackId();
        Session session = null;
        if (StringUtils.isBlank(trackId)) {
            session = this.createSession();
            this.put(session);
            return session;
        }
        session = this.getSession(trackable.getTrackId(), session);
        if (session == null) {
            session = this.createSession(trackable.getTrackId());
            this.put(session);
            return session;
        }
        else {
            session.setFactory(this);
        }

        return session;
    }

    /**
     * <p>
     * 根据追踪ID从MemCached缓存中获取会话对象。
     * </p>
     *
     * @param trackId 追踪ID
     * @param session
     * @return
     */
    private Session getSession(String trackId, Session session) {
        try {
            if (StringUtils.isBlank(trackId))
                throw new IllegalArgumentException("trackId is blank.");
            session = memClient.get(trackId);
        }
        catch (TimeoutException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (MemcachedException e) {
            e.printStackTrace();
        }
        return session;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.skymobi.pcsuit.commons.session.SessionFactory#put(com.skymobi.pcsuit.commons.session.Session)
     */
    @Override
    public void put(Session session) {
        try {
            if (session == null)
                throw new NullPointerException("session is null.");
            memClient.set(session.getId(), expireTime, session);
        }
        catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (MemcachedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.skymobi.pcsuit.commons.session.SessionFactory#remove(com.skymobi.pcsuit.commons.session.Trackable)
     */
    @Override
    public void remove(Trackable trackable) {
        try {
            if (trackable == null || StringUtils.isBlank(trackable.getTrackId()))
                throw new IllegalArgumentException("trackable is null or trackId is blank.");
            memClient.delete(trackable.getTrackId());
        }
        catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (MemcachedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.skymobi.pcsuit.commons.session.SessionFactory#refresh()
     */
    @Override
    public void flush() {
        try {
            memClient.flushAll();
        }
        catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (MemcachedException e) {
            throw new RuntimeException(e);
        }
    }

}
