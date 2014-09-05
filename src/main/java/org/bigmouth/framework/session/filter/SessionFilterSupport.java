/*
 * 文件名称: SessionFilterSupport.java
 * 版权信息: Copyright 2005-2013 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2013-3-6
 * 修改内容: 
 */
package org.bigmouth.framework.session.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.bigmouth.framework.session.Session;
import org.bigmouth.framework.session.SessionFactory;
import org.bigmouth.framework.session.consts.SessionConstants;
import org.bigmouth.framework.session.impl.DefaultSessionImpl;
import org.bigmouth.framework.session.impl.DefaultTrackableImpl;
import org.bigmouth.framework.session.util.SessionUtils;
import org.bigmouth.framework.session.util.UUIDUtils;

/**
 * <p>
 * 会话过滤器。需继承使用，并实现抽象接口。
 * </p>
 * 子类过滤器例子：
 * <pre>
 * 
 * public class SecurityFilter extends SessionFilterSupport {
 * 
 *     private SessionFactory factory = SpringContextHolder.getBean(&quot;pcsuitSessionFactory&quot;);
 * 
 *     public void init(FilterConfig config) throws ServletException {
 *         // Do something..
 *     }
 * 
 *     public void destory() {
 *         // Do something..
 *     }
 * 
 *     protected SessionFactory getSessionFactory() {
 *         return this.factory;
 *     }
 * 
 *     public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
 *             ServletException {
 *         HttpServletRequest req = (HttpServletRequest) request;
 *         HttpServletResponse res = (HttpServletResponse) response;
 *         Session session = getSession(req, res);
 *         if (isLogin(session)) {
 *             // 已经登录
 *             chain.doFilter(request, response);
 *         }
 *     }
 * 
 * }
 * </pre>
 * 
 * @author Allen.Hu / 2013-3-6
 */
public abstract class SessionFilterSupport implements Filter {

    public static final int COOKIE_MAX_AGE = -1;

    public static final String COOKIE_PATH = "/";

    protected SessionFactory factory;

    @Override
    public abstract void destroy();

    /**
     * <p>
     * 不管用户是否登录，都会返回一个用户唯一会话设置在HttpServletResponse响应Cookie中。 
     * 每次客户端请求都会发送Cookie来获取用户是否已经登录等验证操作。
     * </p>
     * 
     * @param request
     * @param response
     * @return
     */
    protected Session getSession(HttpServletRequest request, HttpServletResponse response) {
        factory = getSessionFactory();
        if (factory == null)
            throw new RuntimeException("factory is null.");

        Session session = null;

        String sid = SessionUtils.getInstance(factory).getSessionIDByCookie(request);

        if (StringUtils.isBlank(sid)) {
            sid = UUIDUtils.getUUID();
            Cookie myCookie = new Cookie(SessionConstants.COOKIE_NAME, sid);
            myCookie.setMaxAge(COOKIE_MAX_AGE);
            myCookie.setPath(COOKIE_PATH);

            response.addCookie(myCookie);

            session = new DefaultSessionImpl(sid, factory);
        }
        else {
            session = factory.getSession(new DefaultTrackableImpl(sid));
            session.update(new Date().getTime());
        }
        factory.put(session);
        return session;
    }

    /**
     * <p>
     * 根据会话判断是否已经登录。
     * </p>
     *
     * @param session
     * @return
     */
    protected boolean isLogin(Session session) {
        return (SessionUtils.getInstance(factory).isLogin(session.getId()));
    }

    @Override
    public abstract void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException,
            ServletException;

    @Override
    public abstract void init(FilterConfig arg0) throws ServletException;

    protected abstract SessionFactory getSessionFactory();
}
