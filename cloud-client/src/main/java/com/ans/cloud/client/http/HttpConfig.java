package com.ans.cloud.client.http;

/**
 * Created by anzhen on 2016/6/9.
 */
public class HttpConfig {

    private int connectTimeout = 5*1000;

    private int readTimeout = 5*1000;

    private int retry = 1;

    private String endpoint = "https://inf-api.jcloud.com/";

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
