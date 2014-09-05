/*
 * 文件名称: DaoSupport.java
 * 版权信息: Copyright 2012 Big-mouth framework All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2012-7-2
 * 修改内容: 
 */
package org.bigmouth.framework.dao.support;

import java.io.Serializable;
import java.util.List;

import org.bigmouth.framework.dao.page.PageInfo;


/**
 * <p>
 * MyBatis Dao 基类接口，动态代理Dao接口对某个对象（数据库表）的操作可直接集成该接口。
 * </p>
 * <p>
 * 该基类接口提供了一些基础的功能，比如CURD操作，分页查询。
 * 子接口无需重写这些方法，直接在MyBatis的mapper.xml实现即可。
 * </p>
 * 
 * @author Allen.Hu / 2012-7-2
 * @since Bigmouth-Framework 1.0
 */
public interface IDaoSupport<T, PK extends Serializable> {

    /**
     * 新增一个对象。
     * 
     * @param object
     * @author Allen.Hu / 2012-7-2 
     * @since Bigmouth-Framework 1.0
     */
    void insert(T object);
    
    /**
     * 删除一个对象。
     * 
     * @param id
     * @author Allen.Hu / 2012-7-2 
     * @since Bigmouth-Framework 1.0
     */
    void delete(PK id);
    
    /**
     * 修改一个对象。
     * 
     * @param object
     * @author Allen.Hu / 2012-7-2 
     * @since Bigmouth-Framework 1.0
     */
    void update(T object);
    
    /**
     * 根据唯一标识获得该对象信息。
     * 
     * @param id
     * @return
     * @author Allen.Hu / 2012-7-2 
     * @since Bigmouth-Framework 1.0
     */
    T query(PK id);
    
    /**
     * 无条件查询所有的对象(对于大数据量的数据库表，请慎用此方法，建议使用分页查询)。
     * 
     * @return
     * @author Allen.Hu / 2012-7-2 
     * @since Bigmouth-Framework 1.0
     */
    List<T> queryAll();
    
    /**
     * 根据分页信息查询对象。
     * 
     * @param pageInfo
     * @return
     * @author Allen.Hu / 2012-7-2 
     * @since Bigmouth-Framework 1.0
     */
    List<T> queryAll(PageInfo<T> pageInfo);
}
