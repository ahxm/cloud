package com.ans.cloud.data.model;

/**
 * Created by anzhen on 2016/5/29.
 */
public class ClusterUser extends BaseModel {
    // 登陆名称
    private String pin;
    // 集群ID
    private long clusterId;
    // 集群中的租户名称
    private String tenantName;
    // 集群中的租户ID
    private String tenantId;
    // 集群中的用户ID
    private String userId;
    // 集群中的用户名称
    private String name;
    // 集群中的用户密码
    private String password;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public long getClusterId() {
        return clusterId;
    }

    public void setClusterId(long clusterId) {
        this.clusterId = clusterId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
