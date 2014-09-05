package org.bigmouth.framework.web.action;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * Struts2 CRUD Action的抽象基类.
 * 
 * 主要定义了对Preparable,ModelDriven接口的使用,以及CRUD函数和返回值的命名.
 * 
 * 
 * Result Types:: Chain Result 用来处理Action链 Dispatcher Result 用来转向页面，通常处理JSP FreeMarker Result 处理FreeMarker模板 HttpHeader
 * Result用来控制特殊的Http行为 Redirect Result重定向到一个URL RedirectAction Result重定向到一个Action Stream
 * Result向浏览器发送InputSream对象，通常用来处理文件下载 Velocity Result处理Velocity模板 XLS Result处理XML/XLST模板 PlainText
 * Result显示原始文件内容，例如文件源代码 S2PLUGINS:Tiles Result结合Tile使用
 * 
 */
public abstract class SimpleActionSupport<T> extends BasicActionSupport implements ModelDriven<T>, Preparable {

    private static final long serialVersionUID = 1L;

    /** 进行增删改操作后,以redirect方式重新打开action默认页的result名. */
    public static final String RELOAD = "reload";

    @Override
    public String execute() throws Exception {
        return list();
    }

    public abstract String list() throws Exception;

    @Override
    public String input() throws Exception {
        // Empty Handler
        return INPUT;
    }

    public String save() throws Exception {
        // Empty Handler
        return RELOAD;
    }

    public String delete() throws Exception {
        // Empty Handler
        return RELOAD;
    }

    /**
     * 在input()前执行二次绑定.
     */
    public void prepareInput() throws Exception {
        prepareModel();
    }

    /**
     * 在save()前执行二次绑定.
     */
    public void prepareSave() throws Exception {
        prepareModel();
    }

    public void prepareDelete() throws Exception {
        prepareModel();
    }

    /**
     * 实现空的prepare()函数,屏蔽所有Action函数公共的二次绑定.
     */
    public void prepare() throws Exception {
    }

    /**
     * 等同于prepare()的内部函数,供prepardMethodName()函数调用.
     */
    protected abstract void prepareModel() throws Exception;

    /*
     * 得到ActionMapping中的参数,如果参数为空，就默认返回id的值
     */
    protected String getParams(Object... key) throws Exception {
        ActionMapping mapping = ServletActionContext.getActionMapping();
        Map<String, Object> map = mapping.getParams();
        if (null != map) {
            if (key.length == 0) {
                if (map.containsKey("id")) {
                    Object v = map.get("id");
                    return v != null ? v.toString() : "";
                }
                return "";
            }
            else {
                Object v = map.get(key[0].toString());
                return v != null ? v.toString() : "";
            }
        }
        return "";
    }

}
