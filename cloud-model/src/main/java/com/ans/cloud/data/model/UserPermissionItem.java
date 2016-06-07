package com.ans.cloud.data.model;

import java.io.Serializable;

/**
 * 用户资源访问权限的单项设置
 * Created by anzhen on 2016/2/18.
 */
public class UserPermissionItem implements Serializable {
    // 资源
    private String resource;
    // 权限类型 'R','W','RW'
    private String permissionType;

    public UserPermissionItem() {
    }

    public UserPermissionItem(String resource, String permissionCode) {
        this.resource = resource;
        this.permissionType = permissionCode;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

}
