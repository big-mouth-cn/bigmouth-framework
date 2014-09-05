/*
 * 文件名称: Trackable.java
 * 版权信息: Copyright 2005-2013 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2013-3-5
 * 修改内容: 
 */
package org.bigmouth.framework.session;


/**
 * 会话追踪接口。
 * 
 * @author Allen.Hu / 2013-3-5
 */
public interface Trackable {
    
    /**
     * <p>
     * 返回会话追踪编号。
     * </p>
     *
     * @return
     */
    String getTrackId();

}
