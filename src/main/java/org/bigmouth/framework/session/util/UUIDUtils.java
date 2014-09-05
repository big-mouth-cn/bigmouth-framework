/*
 * 文件名称: UUIDUtils.java
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

import java.util.UUID;


/**
 * UUID工具类。
 * 
 * @author Allen.Hu / 2013-3-5
 */
public class UUIDUtils {

    /**
     * <p>
     * 返回一个随机的UUID字符串。
     * </p>
     * <pre>
     * UUIDUtils.getUUID(); = "61BE7781F6D343619850EB45F9D2332B"
     * </pre>
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }
    
}
