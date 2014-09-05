/*
 * 文件名称: AbstractSubject.java
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

import java.util.ArrayList;
import java.util.List;


/**
 * 抽象的主题。提供了主题接口的基本实现. 具体观察主题可以实现该抽象基类.
 * @author Huxiao created on 2012-3-26
 * @since Observer Framework 1.0
 */
public abstract class AbstractSubject<T> implements Subject<T> {

    private List<Observer<T>> observers;
    
    public AbstractSubject() {
        observers = new ArrayList<Observer<T>>();
    }
    
    @Override
    public abstract Class<T> getSubjectType();

    @Override
    public void notifyObserverAdd(Object message) {
        for (Observer<T> ob : observers) {
            try {
                ob.notifyForAdd(message);
            }
            catch (Exception e) {
                ;
            }
        }
    }

    @Override
    public void notifyObserverDelete(Object message) {
        for (Observer<T> ob : observers) {
            try {
                ob.notifyForDelete(message);
            }
            catch (Exception e) {
                ;
            }
        }
    }

    @Override
    public void notifyObserverModify(Object message) {
        for (Observer<T> ob : observers) {
            try {
                ob.notifyForModify(message);
            }
            catch (Exception e) {
                ;
            }
        }
    }

    @Override
    public void registerObserver(Observer<T> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<T> observer) {
        observers.remove(observer);
    }

}
