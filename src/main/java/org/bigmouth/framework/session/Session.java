/*
 * 文件名称: Session.java
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
 * 会话对象接口。
 * 
 * @author Allen.Hu / 2013-3-4
 */
public interface Session {
    
    /**
     * <p>
     * 返回会话ID。
     * </p>
     *
     * @return
     */
    String getId();
    
    /**
     * <p>
     * 设置参数对象。
     * </p>
     *
     * @param key 主键
     * @param value 对象
     */
    void setAttribute(String key, Object value);
    
    /**
     * <p>
     * 返回指定主键的对象。
     * </p>
     *
     * @param key 主键
     * @return
     */
    Object getAttribute(String key);
    
    /**
     * <p>
     * 返回指定主键的对象是否存在。
     * </p>
     *
     * @param key
     * @return
     */
    boolean exist(String key);
    
    /**
     * <p>
     * 返回该会话是否为新的。
     * </p>
     *
     * @return
     */
    boolean isNew();
    
    /**
     * <p>
     * 返回该会话最后被激活的时间。
     * </p>
     *
     * @return
     */
    long getLastActiveTime();
    
    /**
     * <p>
     * 更新该会话的激活时间。
     * </p>
     *
     * @param time
     */
    void update(long time);
    
    /**
     * <p>
     * 设置该会话的控制接口。
     * </p>
     *
     * @param factory
     */
    void setFactory(SessionFactory factory);
    
    /**
     * <p>
     * 销毁该会话。
     * </p>
     *
     */
    void destroy();

}
