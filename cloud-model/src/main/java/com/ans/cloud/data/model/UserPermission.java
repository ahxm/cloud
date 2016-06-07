package com.ans.cloud.data.model;

/**
 * 用户和权限配置关系
 * Created by anzhen on 2016/2/18.
 */
public class UserPermission extends BaseModel {
    private String account;
    private String pin;
    private long permissionId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }
}
