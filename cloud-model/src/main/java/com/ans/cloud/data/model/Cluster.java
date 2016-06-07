package com.ans.cloud.data.model;

/**
 * Created by anzhen on 2016/5/29.
 */
public class Cluster extends BaseModel {
    public static final String RDS = "rds";

    public static final String ECS = "ecs";

    public static final String V_3 = "v3";

    public static final String V_2 = "v2";

    private String dataCenter;

    private String type;
    // 链接
    private String url;
    // 版本
    private String version;
    // 管理员
    private String admin;
    // 管理员密码
    private String adminPassword;
    // 管理员租户或项目
    private String adminTenant;
    // 管理员地址
    private String adminUrl;
    // 说明
    private String description;

    public String getDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminTenant() {
        return adminTenant;
    }

    public void setAdminTenant(String adminTenant) {
        this.adminTenant = adminTenant;
    }

    public String getAdminUrl() {
        return adminUrl;
    }

    public void setAdminUrl(String adminUrl) {
        this.adminUrl = adminUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }





}
