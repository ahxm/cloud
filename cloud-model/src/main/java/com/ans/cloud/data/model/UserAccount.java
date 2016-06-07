package com.ans.cloud.data.model;

/**
 * Created by anzhen on 2016/6/7.
 */
public class UserAccount extends BaseModel {
    // 用户PING
    protected String pin;
    // 描述
    protected String description;
    // 主账户
    private String account;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
