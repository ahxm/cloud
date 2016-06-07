package com.ans.cloud.model;

import com.ans.cloud.data.model.Query;

/**
 * 权限操作log
 * Created by anzhen on 2016/2/19.
 */
public class QPermissionLog implements Query {
    //主账户的Id
    private String account;
    //权限id
    private long permissionId;
    //对象类型：0：用户，1：组
    private int targetType;
    //对象的名字
    private String targetName;
    //操作类型0：解除；1添加
    private int operationType;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }
}
