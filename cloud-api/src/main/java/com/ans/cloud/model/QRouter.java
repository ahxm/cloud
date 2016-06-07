package com.ans.cloud.model;

import com.ans.cloud.data.model.Query;

/**
 * @author anzhen
 * @since 2016-01-21 17:17
 */
public class QRouter implements Query {
    private String tenantId;

    public static QRouter create() {
        return new QRouter();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public QRouter tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }
}
