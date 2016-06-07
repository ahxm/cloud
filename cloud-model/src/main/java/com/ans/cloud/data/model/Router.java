package com.ans.cloud.data.model;

/**
 * @author anzhen
 * @since 2016-01-21 17:10
 */
public class Router extends BaseModel {
    private String routerId;

    private String routerIp;

    private String tenantId;

    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId;
    }

    public String getRouterIp() {
        return routerIp;
    }

    public void setRouterIp(String routerIp) {
        this.routerIp = routerIp;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
