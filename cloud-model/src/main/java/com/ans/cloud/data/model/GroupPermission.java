package com.ans.cloud.data.model;

/**
 * Created by anzhen on 2016/6/7.
 */
public class GroupPermission extends BaseModel{

    private long groupId;
    private long permissionId;
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }
}
