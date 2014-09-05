/*
 * 文件名称: ErrorNotifySubject.java
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

import org.bigmouth.framework.notify.AbstractSubject;


/**
 * 错误通知主题。
 * @author Huxiao created on 2012-4-5
 * @since Observer Framework 1.0
 */
public class ErrorNotifySubject extends AbstractSubject<ErrorNotify> {

    @Override
    public Class<ErrorNotify> getSubjectType() {
        return ErrorNotify.class;
    }

}
