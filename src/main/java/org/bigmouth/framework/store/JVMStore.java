/*
 * 文件名称: StaticMapStore.java
 * 版权信息: Copyright 2005-2013 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2013-3-16
 * 修改内容: 
 */
package org.bigmouth.framework.store;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存在JVM内存里的数据。
 * 
 * @author Allen.Hu / 2013-3-16
 */
public class JVMStore {

    public interface Key {

    }

    private volatile Map<String, Object> data = null;

    private static volatile JVMStore store = null;

    private JVMStore() {
        this.data = new HashMap<String, Object>();
    }

    public static JVMStore getInstance() {
        if (store == null) {
            synchronized (JVMStore.class) {
                if (store == null)
                    store = new JVMStore();
                return store;
            }
        }
        return store;
    }

    public void put(String key, Object value) {
        this.data.put(key, value);
    }

    public Object get(String key) {
        return this.data.get(key);
    }

    public boolean containsKey(String key) {
        return this.data.containsKey(key);
    }

    public void remove(String key) {
        this.data.remove(key);
    }
}
