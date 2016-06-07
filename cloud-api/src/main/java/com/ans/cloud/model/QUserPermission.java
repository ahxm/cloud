package com.ans.cloud.model;

import com.ans.cloud.data.model.Query;

/**
 * Created by anzhen on 2016/6/7.
 */
public class QUserPermission implements Query {

    private String account;
    private String pin;

    public QUserPermission(){

    }

    public QUserPermission(String account,String pin){
        this.account = account;
        this.pin = pin;
    }

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
}
