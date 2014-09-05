/*
 * 文件名称: BaseEntity.java
 * 版权信息: Copyright 2005-2013 Allen.Hu Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2013-6-22
 * 修改内容: 
 */
package org.bigmouth.framework.entity;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class IdEntity implements Serializable {

    private static final long serialVersionUID = -2860132926137506528L;

    @Expose protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
