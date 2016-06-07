package com.ans.cloud.data.model;

/**
 * Created by anzhen on 2016/2/18.
 */
public class Group extends BaseModel {
    //组名
    private String name;
    //组所归属的主账户User ID
    private String account;
    // 备注
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
