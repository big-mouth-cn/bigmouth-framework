/*
 * 文件名称: Struts2Utils.java
 * 版权信息: Copyright 2012 Big-mouth Metro Labs. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2012-8-16
 * 修改内容: 
 */
package org.bigmouth.framework.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;


/**
 * Struts2 工具
 * 
 * @author Allen.Hu / 2012-8-16
 */
public class Struts2Utils {
    
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    public static HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }
    
    public static HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }
    
    public static HttpSession getSession() {
        return getRequest().getSession();
    }
    
    public static String getRealPath() {
        return ServletActionContext.getServletContext().getRealPath("/");
    }
    
    public static String getContextPath() {
        return getRequest().getContextPath();
    }
}
