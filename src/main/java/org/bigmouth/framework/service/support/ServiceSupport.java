/*
 * 文件名称: ServiceSupport.java
 * 版权信息: Copyright 2012 Big-mouth framework. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2012-8-10
 * 修改内容: 
 */
package org.bigmouth.framework.service.support;

import java.io.Serializable;
import java.util.List;

import org.bigmouth.framework.dao.page.PageInfo;
import org.bigmouth.framework.dao.support.IDaoSupport;

/**
 * 业务层抽象类，子类可继承该类或者实现<code>{@link IServiceSupport}</code>基类接口。
 * 该抽象封装了公共的CURD方法。
 * 
 * @author Allen.Hu / 2012-8-10
 * @since Bigmouth-Framework 1.0
 */
@Deprecated
public abstract class ServiceSupport<T, PK extends Serializable, Dao extends IDaoSupport<T, PK>> {

    /**
     * 获取数据操作接口。
     * 
     * @return
     * @author Allen.Hu / 2012-8-10 
     * @since MetroLabs 1.0
     */
    protected abstract Dao getDao();

    /**
     * 新增一个对象。
     * 
     * @param object
     * @author Allen.Hu / 2012-8-10
     * @since Bigmouth-Framework 1.0
     */
    public void insert(T object) {
        getDao().insert(object);
    }

    /**
     * 删除一个对象。
     * 
     * @param id
     * @author Allen.Hu / 2012-8-10
     * @since Bigmouth-Framework 1.0
     */
    public void delete(PK id) {
        getDao().delete(id);
    }

    /**
     * 修改一个对象。
     * 
     * @param object
     * @author Allen.Hu / 2012-8-10
     * @since Bigmouth-Framework 1.0
     */
    public void update(T object) {
        getDao().update(object);
    }

    /**
     * 根据唯一标识获得该对象信息。
     * 
     * @param id
     * @return
     * @author Allen.Hu / 2012-8-10
     * @since Bigmouth-Framework 1.0
     */
    public T query(PK id) {
        return getDao().query(id);
    }

    /**
     * 无条件查询所有的对象(如果数据源数据量过于庞大，请慎用此方法，建议使用分页查询)。
     * 
     * @return
     * @author Allen.Hu / 2012-8-10
     * @since Bigmouth-Framework 1.0
     */
    public List<T> queryAll() {
        return getDao().queryAll();
    }

    /**
     * 根据分页信息查询对象。
     * 
     * @param pageInfo
     * @return
     * @author Allen.Hu / 2012-8-10
     * @since Bigmouth-Framework 1.0
     */
    public List<T> queryAll(PageInfo pageInfo) {
        return getDao().queryAll(pageInfo);
    }

}
