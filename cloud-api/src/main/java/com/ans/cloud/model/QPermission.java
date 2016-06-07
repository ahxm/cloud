package com.ans.cloud.model;

import com.ans.cloud.data.model.Permission;
import com.ans.cloud.data.model.Query;

/**
 * Created by anzhen on 2016/6/7.
 */
public class QPermission implements Query {
    //0：查所有
    public static final int TYPE_ALL = 0;


    //0：查所有，1：系统权限；2：私有权限
    private int type = 0;
    //所有者的User ID
    private String account;
    private String keyword;

    public String getAccount() {
        if (type == Permission.TYPE_SYS) {
            //如果条件是查询系统权限，永远返回0
            return "";
        }
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        if (type < 0) {
            throw new IllegalArgumentException("QPermission type must >=0");
        }
        this.type = type;
    }
}
