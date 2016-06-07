package com.ans.cloud.model;

import com.ans.cloud.data.model.Query;

/**
 * Created by anzhen on 2016/6/7.
 */
public class QGroupPermission implements Query {
    //组ID查询条件
    private long groupId;
    //主账户id
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

}
