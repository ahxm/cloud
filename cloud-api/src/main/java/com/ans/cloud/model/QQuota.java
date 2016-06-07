package com.ans.cloud.model;

import com.ans.cloud.data.model.Query;

/**
 * 查询配额
 * Created by anzhen on 16-1-22.
 */
public class QQuota implements Query {
    // 用户ID
    private long userId;
    // 资源ID
    private long resourceId;
    // 资源名称
    private String resourceCode;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }
}
