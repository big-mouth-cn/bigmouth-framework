/*
 * 文件名称: UrlParamsInterceptor.java
 * 版权信息: Copyright 2012 Big-mouth framework. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2012-8-3
 * 修改内容: 
 */
package org.bigmouth.framework.interceptor;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;


/**
 * @author Allen.Hu / 2012-8-3
 * @since Bigmouth-Framework 1.0
 */
public class UrlParamsInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 1L;

    /**
     * (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
     */
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ActionMapping mapping = ServletActionContext.getActionMapping();
        //Action action = (Action)invocation.getAction();
        Map<String, Object> map = mapping.getParams();
        if (map != null && map.size() > 0) {
            ValueStack stack = invocation.getInvocationContext()
                    .getValueStack();
            for (Object o : map.keySet()) {
                stack.setValue(o.toString(), map.get(o));
            }
            invocation.getInvocationContext().setValueStack(stack);
        }
        return invocation.invoke();
    }

}
