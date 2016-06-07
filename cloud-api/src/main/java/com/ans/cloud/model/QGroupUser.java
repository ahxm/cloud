package com.ans.cloud.model;

import com.ans.cloud.data.model.Query;

/**
 * Created by anzhen on 2016/6/7.
 */
public class QGroupUser implements Query {

    /**
     * 查询类型：0根据组id查询组内的用户,此时QGroupUser中id应该是group id；
     */
    public static final int TYPE_BY_GROUP = 0;

    /**
     * 查询类型：按照用户id查询用户加入的组，此时QGroupUser中id的值应该是user id
     */
    public static final int TYPE_BY_USER = 1;
    //查询类型：0根据组id查询组内的用户,此时id应该是组id；1按照用户id查询用户加入的组，此时id的值应该是user id
    private int type = TYPE_BY_GROUP;
    private String account;
    //组id
    private long groupId;
    //子用户pin
    private String pin;

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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
