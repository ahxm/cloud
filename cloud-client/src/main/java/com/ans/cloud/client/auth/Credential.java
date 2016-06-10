package com.ans.cloud.client.auth;

/**
 * 身份认证信息
 * Created by anzhen on 2016/6/8.
 */
public class Credential {

    private String accessKey;

    private String accessSecret;

    public Credential(String accessKey, String accessSecret) {
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }
}
