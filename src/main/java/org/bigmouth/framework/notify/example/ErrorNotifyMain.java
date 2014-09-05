/*
 * 文件名称: ErrorNotifyMain.java
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

import org.bigmouth.framework.notify.SubjectPark;


/**
 * 示例入口。
 * @author Huxiao created on 2012-4-5
 * @since Observer Framework 1.0
 */
public class ErrorNotifyMain {

    public static void main(String[] args) {
        // 获得主题公园实例。
        SubjectPark park = SubjectPark.getInstance();
        // 注册错误通知观察者及主题。
        park.registerSubject(new ErrorNotifySubject());
        park.registerObserver(new ErrorNotifyObserver());
        // 通过框架发送错误(ErrorNotify)通知，通知主题类型为新增。
        // 如此一来，所有的(ErrorNotify)对象的观察者都会收到新增通知，
        // 然后调用自己的ADD接口实现。
        park.notificationObservers(new ErrorNotify(), SubjectPark.ADD);
    }
}
