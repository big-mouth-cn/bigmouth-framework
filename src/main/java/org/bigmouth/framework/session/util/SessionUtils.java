/*
 * 文件名称: SessionUtils.java
 * 版权信息: Copyright 2005-2013 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2013-3-5
 * 修改内容: 
 */
package org.bigmouth.framework.session.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.bigmouth.framework.session.Session;
import org.bigmouth.framework.session.SessionFactory;
import org.bigmouth.framework.session.Trackable;
import org.bigmouth.framework.session.consts.SessionConstants;

/**
 * <p>
 * 会话工具类。
 * </p>
 * 
 * <pre>
 * SessionUtils util = SessionUtils.getInstance();
 * </pre>
 * 
 * @author Allen.Hu / 2013-3-5
 */
public class SessionUtils {

    private static SessionUtils sessionUtils;

    private SessionFactory factory;

    public SessionUtils(SessionFactory factory) {
        super();
        this.factory = factory;
    }

    public static SessionUtils getInstance(SessionFactory factory) {
        if (sessionUtils == null)
            sessionUtils = new SessionUtils(factory);
        return sessionUtils;
    }

    /**
     * <p>
     * 根据Cookie唯一会话编号判断是否已经登录。
     * </p>
     * 
     * @param sid
     * @return
     */
    public boolean isLogin(final String sid) {
        Session session = getSession(sid);
        return existAttribute(session, SessionConstants.ATTRIBUTE_NAME);
    }
    
    /**
     * 根据HttpServletRequest对象判断是否已经登录
     * @param request
     * @return
     */
    public boolean isLogin(HttpServletRequest request) {
    	String sid = getSessionIDByCookie(request);
    	return isLogin(sid);
    }

    /**
     * <p>
     * 保存登录对象信息。
     * </p>
     * 
     * @param request
     * @param object 对象信息
     * @see SessionConstants.ATTRIBUTE_NAME
     */
    public void saveLogin(HttpServletRequest request, Object object) {
        this.setAttribute(request, SessionConstants.ATTRIBUTE_NAME, object);
    }
    
    /**
     * <p>
     * 销毁登录对象信息。
     * </p>
     *
     * @param request
     */
    public void removeLogin(HttpServletRequest request) {
        this.destroy(request);
    }

    /**
     * <p>
     * 返回会话中是否存在某主键的值。
     * </p>
     * 
     * @param session
     * @param key
     * @return
     */
    private boolean existAttribute(Session session, String key) {
        Object obj = session.getAttribute(key);
        return obj != null;
    }

    /**
     * <p>
     * 设置当前请求会话指定键值对象到缓存中。
     * </p>
     *
     * @param request
     * @param key
     * @param value
     */
    public void setAttribute(HttpServletRequest request, String key, Object value) {
        String id = this.getSessionIDByCookie(request);
        Session session = getSession(id);
        session.setAttribute(key, value);
        factory.put(session);
    }
    
    /**
     * <p>
     * 销毁会话。
     * </p>
     *
     * @param request
     */
    public void destroy(HttpServletRequest request) {
        String id = this.getSessionIDByCookie(request);
        Session session = getSession(id);
        session.destroy();
    }

    /**
     * <p>
     * 返回当前请求的会话对象。
     * </p>
     *
     * @param request
     * @return
     */
    public Session getSession(HttpServletRequest request) {
        String id = getSessionIDByCookie(request);
        return getSession(id);
    }

    /**
     * <p>
     * 返回指定会话编号的会话对象。
     * </p>
     *
     * @param id
     * @return
     */
    public Session getSession(final String id) {
        Session session = factory.getSession(new Trackable() {
            public String getTrackId() {
                return id;
            }
        });
        return session;
    }

    /**
     * <p>
     * 从请求对象中获取会话编号。
     * </p>
     *
     * @param request
     * @return
     */
    public String getSessionIDByCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String sid = "";
        if (cookies != null && cookies.length > 0) {
            for (Cookie sCookie : cookies) {
                if (sCookie.getName().equalsIgnoreCase(SessionConstants.COOKIE_NAME)) {
                    sid = sCookie.getValue();
                }
            }
        }
        return sid;
    }
}
