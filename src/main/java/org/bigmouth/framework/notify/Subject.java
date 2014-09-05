/*
 * 文件名称: Subject.java
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
 * 主题接口。
 * @author Huxiao created on 2012-3-26
 * @since Observer Framework 1.0
 */
public interface Subject<T> {

    /**
     * 获取消息主题的类型。
     * @return
     * @author Huxiao created on 2012-3-26 
     * @since Observer Framework 1.0
     */
    Class<T> getSubjectType();
    
    /**
     * 注册观察者，该观察者必须与主题对应同样的消息类类型。
     * @param observer
     * @author Huxiao created on 2012-3-26 
     * @since Observer Framework 1.0
     */
    void registerObserver(Observer<T> observer);
    
    /**
     * 移除观察者，该观察者必须与主题对应同样的消息类型。
     * @param observer
     * @author Huxiao created on 2012-3-26 
     * @since Observer Framework 1.0
     */
    void removeObserver(Observer<T> observer);
    
    /**
     * 通知所有观察者有消息新增。
     * @param message
     * @author Huxiao created on 2012-3-26 
     * @since Observer Framework 1.0
     */
    void notifyObserverAdd(Object message);
    
    /**
     * 通知所有观察者有消息修改。
     * @param message
     * @author Huxiao created on 2012-3-26 
     * @since Observer Framework 1.0
     */
    void notifyObserverModify(Object message);
    
    /**
     * 通知所有观察者有消息删除。
     * @param message
     * @author Huxiao created on 2012-3-26 
     * @since Observer Framework 1.0
     */
    void notifyObserverDelete(Object message);
}
