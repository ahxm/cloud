package com.ans.cloud.model;

import com.ans.cloud.data.model.Query;

/**
 * Created by anzhen on 2016/2/15.
 */
public class QUserQuotaID implements Query {
    private long id;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id=id;
    }
}
