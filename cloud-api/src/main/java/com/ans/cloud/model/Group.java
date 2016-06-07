package com.ans.cloud.model;

import com.ans.cloud.data.model.BaseModel;

/**
 * Created by anzhen on 2016/6/7.
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
