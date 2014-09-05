/*
 * 文件名称: MemSessionImpl.java
 * 版权信息: Copyright 2005-2013 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2013-3-5
 * 修改内容: 
 */
package org.bigmouth.framework.session.impl.memcache;

import org.bigmouth.framework.session.SessionFactory;
import org.bigmouth.framework.session.impl.DefaultSessionImpl;

/**
 * 基于MemCached缓存机制的会话对象实现类。
 * 
 * @author Allen.Hu / 2013-3-5
 */
public class MemSessionImpl extends DefaultSessionImpl {

    private static final long serialVersionUID = 1305088632965253298L;

    public MemSessionImpl(String id, SessionFactory factory) {
        super(id, factory);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.skymobi.pcsuit.commons.session.impl.DefaultSessionImpl#setAttribute(java.lang.String, java.lang.Object)
     */
    @Override
    public void setAttribute(String key, Object value) {
        super.setAttribute(key, value);
        this.store();
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.skymobi.pcsuit.commons.session.impl.DefaultSessionImpl#update(long)
     */
    @Override
    public void update(long time) {
        super.update(time);
        this.store();
    }

    private void store() {
        getFactory().put(this);
    }
}
