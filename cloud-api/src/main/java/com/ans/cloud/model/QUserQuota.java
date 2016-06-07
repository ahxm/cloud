package com.ans.cloud.model;

import com.ans.cloud.data.model.Query;

/**
 * @author anzhen
 * @since 2016-02-01 16:31
 */
public class QUserQuota implements Query {
    private int type;

    private long userId;

    // 数据中心 （机房）
    private String dataCenter;

    public String getDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
