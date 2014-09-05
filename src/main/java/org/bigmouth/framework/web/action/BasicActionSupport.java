/*
 * 文件名称: BasicActionSupport.java
 * 版权信息: Copyright 2012 Big-mouth framework. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2012-6-28
 * 修改内容: 
 */
package org.bigmouth.framework.web.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.bigmouth.framework.core.ResponseData;
import org.bigmouth.framework.lang.Constants;
import org.bigmouth.framework.util.DateUtils;
import org.bigmouth.framework.util.Struts2Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;


/**
 * Struts2 Action 基类。封装了一些与前端数据交互方法，有需要可继承使用。
 * 
 * @author Allen.Hu / 2012-6-28
 * @since Bigmouth-Framework 1.0.1
 */
public class BasicActionSupport extends ActionSupport {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    public static final String RELOAD = "reload";
    
    public final static String DEFAULT_CHARSET = Constants.DEFAULT_RESPONSE_CHARSET;

    // -------------------------------- 公共方法 -------------------------------- //
    
    protected void setAttribute(String key, Object value) {
        Struts2Utils.getRequest().setAttribute(key, value);
    }

    protected String getParameter(String name) {
        return Struts2Utils.getParameter(name);
    }

    protected void doResponse(Object respData) throws IOException {
        PrintWriter pw = null;
        try {
            pw = getResponse().getWriter();
            Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat(DateUtils.LONG_DATE_FORMAT)
                .create();
            pw.write(gson.toJson(respData));
            pw.flush();
        }
        finally {
            IOUtils.closeQuietly(pw);
        }
    }
    
    protected void doResponseQuietly(ResponseData respData) {
        try {
            doResponse(respData);
        }
        catch (IOException e) {
            ;
        }
    }

    protected void doResponseObject(int statusCode, String message, Object data) {
        ResponseData responseData = new ResponseData(statusCode, message, data);
        doResponseQuietly(responseData);
    }

    protected void doResponse(int statusCode, String message) {
        doResponseObject(statusCode, message, null);
    }

    protected void doResponse(int statusCode) {
        doResponse(statusCode, "");
    }
    
    /**
     * 返回响应给前端。
     * 
     * @param data 响应内容
     * @throws IOException
     * @author Allen.Hu / 2012-6-28
     * @since Bigmouth-Framework 1.0
     */
    protected void doResponse(byte[] data) throws IOException {
        OutputStream os = null;
        try {
            os = getResponseOutputStream();
            if (null != data) {
                os.write(data, 0, data.length);
            }
            os.flush();
        }
        finally {
            IOUtils.closeQuietly(os);
        }
    }

    /**
     * 返回响应给前端。
     * 
     * @param data 响应字符串内容
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @author Allen.Hu / 2012-6-28
     * @since Bigmouth-Framework 1.0
     */
    protected void doResponse(String data) throws UnsupportedEncodingException, IOException {
        this.doResponse(data.getBytes(DEFAULT_CHARSET));
    }

    /**
     * 返回响应给前端，内部直接捕获异常。
     * 
     * @param data
     * @author Allen.Hu / 2012-6-28
     * @since Bigmouth-Framework 1.0
     */
    protected void doResponseQuietly(byte[] data) {
        try {
            this.doResponse(data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回响应给前端，内部直接捕获异常。
     * 
     * @param data 响应字符串内容
     * @author Allen.Hu / 2012-6-28
     * @since Bigmouth-Framework 1.0
     */
    protected void doResponseQuietly(String data) {
        try {
            this.doResponse(data);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得<code>Response</code>对象输出流。
     * 
     * @return
     * @throws IOException
     * @author Allen.Hu / 2012-6-28
     * @since Bigmouth-Framework 1.0
     */
    protected OutputStream getResponseOutputStream() throws IOException {
        return getResponse().getOutputStream();
    }

    /**
     * 获得<code>Request</code>对象输入流。
     * 
     * @return
     * @throws IOException
     * @author Allen.Hu / 2012-6-28
     * @since Bigmouth-Framework 1.0
     */
    protected InputStream getRequestInputStream() throws IOException {
        return getRequest().getInputStream();
    }

    /**
     * 获得<code>HttpServletResponse</code>对象。
     * 
     * @return
     * @author Allen.Hu / 2012-6-28
     * @since Bigmouth-Framework 1.0
     */
    protected HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    /**
     * 获得<code>HttpServletRequest</code>对象。
     * 
     * @return
     * @author Allen.Hu / 2012-6-28
     * @since Bigmouth-Framework 1.0
     */
    protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    // -------------------------------- 页面跳转标识（ResultName） -------------------------------- //

    /**
     * 页面跳转标识<code>（ResultName）</code>
     * 
     * @author Allen.Hu / 2012-8-9
     * @since MetroLabs 1.0
     */
    public interface ResultName {

        String LIST = "list";
    }

    // -------------------------------- 以下为Getter/Setter方法 -------------------------------- //

}
