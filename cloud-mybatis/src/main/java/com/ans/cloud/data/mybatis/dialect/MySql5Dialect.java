package com.ans.cloud.data.mybatis.dialect;

/**
 * Created by anzhen on 2016/5/27.
 */
public class MySql5Dialect implements Dialect {
    @Override
    public String getLimitString(String sql, int skipResult, int maxResults) {
        return getLimitString(sql,skipResult,Integer.toString(skipResult),Integer.toString(maxResults));
    }

    private String getLimitString(String sql,int offset,String offsetPlaceholder,String limitPlaceholder){
        StringBuilder builder = new StringBuilder(sql);
        builder.append(" limit ");
        if(offset>0){
            builder.append(offsetPlaceholder).append(",").append(limitPlaceholder);
        }else {
            builder.append(limitPlaceholder);
        }
        return builder.toString();
    }

}
