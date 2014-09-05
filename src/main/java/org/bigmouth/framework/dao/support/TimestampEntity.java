/*
 * 文件名称: TimestampEntity.java
 * 版权信息: Copyright 2010-2012 Shanxi-Village Web By Allen Hu. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2012-7-10
 * 修改内容: 
 */
package org.bigmouth.framework.dao.support;

import java.io.Serializable;
import java.util.Date;

import org.bigmouth.framework.lang.Constants;

/**
 * 有时间戳记录的实体对象。
 * 
 * @author Allen.Hu / 2012-7-10
 * @since ShanxiVillage 1.0
 */
public class TimestampEntity implements Serializable {

    protected static final long serialVersionUID = 5735927447114886926L;
    
    public static final String CREATEUSER_SYSTEM = "SYSTEM";

    /** 创建者（用户名） */
    private String createUser;

    /** 创建时间 */
    private Date createTime;

    /** 修改者（用户名） */
    private String modifyUser;

    /** 修改时间 */
    private Date modifyTime;

    /** 逻辑删除标识 */
    private Integer deleted = Constants.Deleted.NO;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

}
