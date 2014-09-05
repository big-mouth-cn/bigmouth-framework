/*
 * 文件名称: SessionFactory.java
 * 版权信息: Copyright 2005-2013 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2013-3-4
 * 修改内容: 
 */
package org.bigmouth.framework.session;


/**
 * 会话管理工厂接口。
 * 
 * @author Allen.Hu / 2013-3-4
 */
public interface SessionFactory {
    
    /**
     * <p>
     * 根据会话追踪编号返回会话对象。
     * </p>
     *
     * @param trackable
     * @return
     */
    Session getSession(Trackable trackable);
    
    /**
     * <p>
     * 保存会话。
     * </p>
     *
     * @param session
     */
    void put(Session session);
    
    /**
     * <p>
     * 移除一个会话。
     * </p>
     *
     * @param trackable
     */
    void remove(Trackable trackable);

    /**
     * <p>
     * 清理所有缓存。
     * </p>
     *
     */
    void flush() ;
}
