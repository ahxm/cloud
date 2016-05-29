package com.ans.cloud.data.mybatis.interceptor;


import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Properties;

/**
 * Created by anzhen on 2016/5/27.
 */
public abstract class PageInterceptor implements Interceptor {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String PAGE_SQL_KEY = "pageSqlKey";
    public static final String COUNT_SQL_KEY = "countSqlKey";

    public static final String DEFAULT_PAGE_SQL = "findByQuery";
    public static final String DEFAULT_COUNT_SQL = "findCountByQuery";

    protected String pageSql = DEFAULT_PAGE_SQL;
    protected String countSql = DEFAULT_COUNT_SQL;

    @Override
    public Object plugin(Object target) {
       return Plugin.wrap(target,null);
    }

    @Override
    public void setProperties(Properties properties) {

        if(properties!=null){
            String sqlId = properties.getProperty(PAGE_SQL_KEY);
            if(sqlId !=null && !sqlId.trim().isEmpty()){
                pageSql = sqlId;
            }

            sqlId = properties.getProperty(COUNT_SQL_KEY);
            if(sqlId!=null && !sqlId.trim().isEmpty()){
                countSql = sqlId;
            }
        }

    }

    protected boolean isPageSql(String statement){
        return statement == null?false:statement.endsWith(pageSql);
    }

    protected String buildCountStatement(String statement) {
       String result =  statement.substring(0, statement.lastIndexOf(pageSql)) + countSql;
        return result;
    }

}
