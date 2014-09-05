/*
 * 文件名称: DefaultPaggingDialectFactory.java
 * 版权信息: Copyright 2005-2012 SKY-MOBI Inc. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: Allen.Hu
 * 修改日期: 2012-6-29
 * 修改内容: 
 */
package org.bigmouth.framework.dao.page;

import org.bigmouth.framework.dao.DbTypes;
import org.bigmouth.framework.dao.page.mysql.MySqlPaggingDialect;
import org.bigmouth.framework.dao.page.oracle.OraclePaggingDialect;
import org.bigmouth.framework.dao.page.postgre.PostgreSqlPaggingDialect;


/**
 * 分页查询方言工厂的默认实现。<br/>
 * 目前支持的数据库有：
 * <ul>
 * <li>Oracle</li>
 * <li>PostgreSql</li>
 * </ul>
 * 
 * @author Allen.Hu / 2012-6-29
 * @since SkyMarket 1.0
 */
public class DefaultPaggingDialectFactory implements PaggingDialectFactory {

    /**
     * (non-Javadoc)
     * 
     * @see com.skymobi.commons.dao.page.PaggingDialectFactory#getPaggingDialect(java.lang.String)
     */
    @Override
    public PaggingDialect getPaggingDialect(String dbType) {
        PaggingDialect dialect = null;
        if (DbTypes.ORACLE.equals(dbType)) {
            dialect = new OraclePaggingDialect();
        }
        else if (DbTypes.POSTGRESQL.equals(dbType)) {
            dialect = new PostgreSqlPaggingDialect();
        }
        else if (DbTypes.MYSQL.equals(dbType)) {
            dialect = new MySqlPaggingDialect();
        }
        return dialect;
    }

}
