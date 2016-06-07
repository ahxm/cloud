package com.ans.cloud.model;

import com.ans.cloud.data.model.Query;

/**
 * Created by anzhen on 2016/4/5.
 */
public class QFlavor implements Query {
    private long userId;
    private String type;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
