package com.ans.cloud.model;

import com.ans.cloud.data.model.Query;

/**
 * Created by anzhen on 2016/6/7.
 */
public class QGroup implements Query {

    private String account;
    private String keyword;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
