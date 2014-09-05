/*
 * 文件名称: NetException.java
 * 版权信息: Copyright 2005-2012 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: allen.hu
 * 修改日期: 2012-9-6
 * 修改内容: 
 */
package org.bigmouth.framework.net;

/**
 * 网络异常。
 * 
 * @author allen.hu / 2012-9-6
 */
public class NetException extends RuntimeException {

    private static final long serialVersionUID = -7783383445380399868L;

    public NetException() {
        super();
    }

    public NetException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public NetException(String arg0) {
        super(arg0);
    }

    public NetException(Throwable arg0) {
        super(arg0);
    }

}
