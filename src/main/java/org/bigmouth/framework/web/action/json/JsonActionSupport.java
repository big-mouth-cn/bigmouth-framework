package org.bigmouth.framework.web.action.json;

import java.util.HashMap;
import java.util.Map;

import org.bigmouth.framework.core.ResponseData;
import org.bigmouth.framework.web.action.BasicActionSupport;

/**
 * <p>
 * 基于JSON格式响应给客户端的Action基类，该类主要是封装一些公共的响应方法。
 * </p>
 * 具体其他实现建议扩展该类。 <br/>
 * JSON数据结构：
 * 
 * <pre>
 *     "Flag" : -1,
 *     "Message" : '保存失败',
 *     "Data" : [
 *          {
 *              ...
 *          }
 *      ]
 * </pre>
 * 
 * @author Allen / 2012-9-28
 */
public class JsonActionSupport extends BasicActionSupport {

    private static final long serialVersionUID = 7378383958484802189L;

    protected void succeed() {
        succeed(new Object());
    }

    protected void succeed(Object data) {
        doResponseObject(ResponseData.SUCCEED, "", data);
    }

    protected void succeed(Map<String, Object> data) {
        doResponseObject(ResponseData.SUCCEED, "", data);
    }

    protected void succeed(String[] keys, Object[] values) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0, len = keys.length; i < len; i++) {
            map.put(keys[i], values[i]);
        }
        succeed(map);
    }

    protected void failed() {
        failed("");
    }

    protected void failed(String message) {
        failed(ResponseData.FAILED, message);
    }

    protected void failed(int statusCode, String message) {
        doResponseObject(statusCode, message, null);
    }
}
