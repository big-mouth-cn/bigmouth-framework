/*
 * 文件名称: SubjectPark.java
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

import java.util.HashMap;
import java.util.Map;


/**
 * 主题公园。注册了所有的观察者消息主题。
 * 在注册观察者时通过该主题公园来完成、并可实现移除观察者及通知观察者。
 * @author Huxiao created on 2012-3-26
 * @since Observer Framework 1.0
 */
public class SubjectPark {

    private static volatile SubjectPark subjectPark;
    
    /** 消息枚举：新增操作 */
    public static final int ADD = 1;
    
    /** 消息枚举：修改操作 */
    public static final int MODIFY = 2;
    
    /** 消息枚举：删除操作 */
    public static final int DELETE = 3;
    
    /** 主题集合：Key为消息类型，Value为主题。 */
    @SuppressWarnings("rawtypes")
    private Map<Class, Subject> subjects;
    
    @SuppressWarnings("rawtypes")
    private SubjectPark() {
        subjects = new HashMap<Class, Subject>();
    }
    
    /**
     * 获得主题管理对象。通过该对象可以注册、移除消息主题及观察者，并发送消息通知。
     * 
     * @return
     * @author Allen.Hu / 2012-6-28 
     * @since SkyMarket 1.0
     */
    public static SubjectPark getInstance() {
        if (subjectPark == null) {
            synchronized (SubjectPark.class) {
                if (subjectPark == null)
                    subjectPark = new SubjectPark();
            }
        }
        return subjectPark;
    }
    
    /**
     * 注册一个消息主题。该主题对象必须是抽象主题的实现。
     * 
     * @param subject
     * @author Allen.Hu / 2012-6-28 
     * @since SkyMarket 1.0
     */
    public void registerSubject(Subject<?> subject) {
        if (subject == null) 
            throw new NotifyException("The Subject can not is null.");
        
        // 未注册的主题或者主题为空时, 进行注册
        if (!subjects.containsKey(subject.getSubjectType()) || subjects.get(subject.getSubjectType()) == null) {
            subjects.put(subject.getSubjectType(), subject);
        }
    }
    
    /**
     * 移除一个消息主题。
     * 
     * @param subject
     * @author Allen.Hu / 2012-6-28 
     * @since SkyMarket 1.0
     */
    public void removeSubject(Subject<?> subject) {
        if (subject == null)
            throw new NotifyException("The Subject can not is null.");
        
        if (subjects.containsKey(subject.getSubjectType())) {
            subjects.remove(subject.getSubjectType());
        }
    }
    
    /**
     * 注册一个具体的观察者。该观察者必须是接口<code>Observer</code>的实现。
     * 
     * @param observer
     * @author Allen.Hu / 2012-6-28 
     * @since SkyMarket 1.0
     * @see org.huxiao.notify.Observer
     */
    @SuppressWarnings("unchecked")
    public void registerObserver(Observer<?> observer) {
        if (observer == null) 
            throw new NotifyException("The Observer can not is null.");
        
        if (subjects.containsKey(observer.getObserverType())) {
            subjects.get(observer.getObserverType()).registerObserver(observer);
        }
    }
    
    /**
     * 移除一个具体的观察者。
     * 
     * @param observer
     * @author Allen.Hu / 2012-6-28 
     * @since SkyMarket 1.0
     */
    @SuppressWarnings("unchecked")
    public void removeObserver(Observer<?> observer) {
        if (observer == null) 
            throw new NotifyException("The Observer can not is null.");
        
        if (subjects.containsKey(observer.getObserverType())) {
            subjects.get(observer.getObserverType()).removeObserver(observer);
        }
    }
    
    /**
     * 发送消息通知，内部会根据参数 message 的类型来通知注册了 message 的所有观察者。
     * 
     * @param message 消息类型
     * @param messageType 消息枚举。请参考此类的常量。
     * @author Allen.Hu / 2012-6-28 
     * @since SkyMarket 1.0
     */
    public void notificationObservers(Object message, int messageType) {
        if (message != null) {
            Class<?> cls = message.getClass();
            if (subjects.containsKey(cls)) {
                Subject<?> subject = subjects.get(cls);
                switch (messageType) {
                    case ADD: {
                        subject.notifyObserverAdd(message);
                        break;
                    }
                    case MODIFY: {
                        subject.notifyObserverModify(message);
                        break;
                    }
                    case DELETE: {
                        subject.notifyObserverDelete(message);
                        break;
                    }
                    default: {
                        throw new NotifyException("Does not support the type of notification.");
                    }
                }
            }
        }
    }
}
