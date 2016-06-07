package com.ans.cloud.data.model;

/**
 * Created by anzhen on 2016/2/18.
 */
public class GroupUser extends BaseModel {
    private long groupId;
    private String account;
    private String pin;

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

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
    
}
