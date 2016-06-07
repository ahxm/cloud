package com.ans.cloud.data.model;

/**
 * Created by anzhen on 2016/2/18.
 */
public class PermissionLog extends BaseModel {
    public static final int TARGET_TYPE_USER = 0;
    public static final int TARGET_TYPE_GROUP = 1;

    public static final int OP_TYPE_REMOVE = 0;
    public static final int OP_TYPE_ADD = 1;
    //主账户pin
    private String account;
    //权限id
    private long permissionId;
    //对象类型：0：用户，1：组
    private int targetType;
    //对象的对应的id
    private long targetId;
    //为了冗余数据，对象的名字
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

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
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
