/*
 * 文件名称: ErrorNotifyObserver.java
 * 版权信息: Copyright 2012 Huxiao. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Huxiao
 * 修改日期: 2012-4-5
 * 修改内容: 
 */
package org.bigmouth.framework.notify.example;

import org.bigmouth.framework.notify.Observer;


/**
 * 错误通知观察者。
 * @author Huxiao created on 2012-4-5
 * @since Observer Framework 1.0
 */
public class ErrorNotifyObserver implements Observer<ErrorNotify> {

    @Override
    public Class<ErrorNotify> getObserverType() {
        return ErrorNotify.class;
    }

    @Override
    public void notifyForAdd(Object message) {
        System.out.println("Notify For Add.");
    }

    @Override
    public void notifyForDelete(Object message) {
        System.out.println("Notify For Delete.");
    }

    @Override
    public void notifyForModify(Object message) {
        System.out.println("Notify For Modify.");
    }

}
