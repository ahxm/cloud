package com.ans.cloud.data.model;

import java.util.Date;

/**
 * @author anzhen
 * @since 2016-03-08 14:13
 */
public class UserAccessKey extends BaseModel {
    // 当前用户pin
    private String pin;
    // 所属主账户
    private String account;

    // API访问key
    private String accessKey;
    // API访问密钥
    private String accessKeySecret;
    // 有效期
    private Date expire;

    public boolean validate() {
        // 状态无效
        if (status <= BaseModel.DISABLED) {
            return false;
        }

        // 配置了有效期且已过期
        if (expire != null && expire.before(new Date())) {
            return false;
        }

        return true;
    }

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

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
}
