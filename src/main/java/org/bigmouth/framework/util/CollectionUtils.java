/*
 * 文件名称: CollectionUtils.java
 * 版权信息: Copyright 2012 Big-mouth framework All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2012-6-29
 * 修改内容: 
 */
package org.bigmouth.framework.util;

import java.util.Collection;

/**
 * 集合工具。
 * 
 * @author Allen.Hu / 2012-6-29
 * @since Bigmouth-Framework 1.0
 */
public class CollectionUtils {

    /** 默认构造函数, 防止实例化 */
    private CollectionUtils() {

    }

    /**
     * 判断集合是否为空。
     * 
     * @param coll 集合, 包括 List, Set 接口.
     * @return true: 集合空; false: 集合非空
     * @author Allen.Hu / 2012-6-29
     * @since Bigmouth-Framework 1.0
     */
    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    /**
     * 判断集合是否非空。
     * 
     * @param coll 集合, 包括 List, Set 接口.
     * @return true: 集合非空; false: 集合空
     * @author Allen.Hu / 2012-6-29
     * @since Bigmouth-Framework 1.0
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }
}
