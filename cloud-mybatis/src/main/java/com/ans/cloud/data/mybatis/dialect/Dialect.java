package com.ans.cloud.data.mybatis.dialect;

/**
 * Created by anzhen on 2016/5/27.
 */
public interface Dialect {

    enum Type{
        MYSQL,
        ORACLE
    }

    String getLimitString(String sql,int skipResult,int maxResults);
}
