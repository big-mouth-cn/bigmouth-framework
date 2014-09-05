/*
 * 文件名称: IServiceSupport.java
 * 版权信息: Copyright 2012 Big-mouth framework. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2012-7-2
 * 修改内容: 
 */
package org.bigmouth.framework.service.i;

import java.io.Serializable;
import java.util.List;

import org.bigmouth.framework.dao.page.PageInfo;
import org.bigmouth.framework.service.support.ServiceSupport;


/**
 * 业务层的基类接口。
 * 该接口封装了一些基础公共的处理方法，比如CURD，分页查询。
 * 如果需要添加其他业务处理方法，请在子接口中添加。
 * <p><b>
 * 建议继承抽象类<code>{@link ServiceSupport}</code>
 * </b></p>
 * 
 * @author Allen.Hu / 2012-7-2
 * @since Bigmouth-Framework 1.0
 */
@Deprecated
public interface IServiceSupport<T, PK extends Serializable> {

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
     * 无条件查询所有的对象(如果数据源数据量过于庞大，请慎用此方法，建议使用分页查询)。
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
    List<T> queryAll(PageInfo pageInfo);
}
