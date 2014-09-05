/*
 * 文件名称: Observer.java
 * 版权信息: Copyright 2012 Huxiao. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Huxiao
 * 修改日期: 2012-3-26
 * 修改内容: 
 */
package org.bigmouth.framework.notify;

/**
 * 观察者接口。 所有的观察者必须实现此接口。
 * 
 * @author Huxiao created on 2012-3-26
 * @since Observer Framework 1.0
 */
public interface Observer<T> {

    /**
     * 获得观察者所观察的消息类型。
     * 
     * @return
     * @author Huxiao created on 2012-3-26
     * @since Observer Framework 1.0
     */
    Class<T> getObserverType();

    /**
     * 通知观察者消息有新增。
     * 
     * @param message
     * @author Huxiao created on 2012-3-26
     * @since Observer Framework 1.0
     */
    void notifyForAdd(Object message);

    /**
     * 通知观察者消息有修改。
     * 
     * @param message
     * @author Huxiao created on 2012-3-26
     * @since Observer Framework 1.0
     */
    void notifyForModify(Object message);

    /**
     * 通知观察者消息有删除。
     * 
     * @param message
     * @author Huxiao created on 2012-3-26
     * @since Observer Framework 1.0
     */
    void notifyForDelete(Object message);
}
