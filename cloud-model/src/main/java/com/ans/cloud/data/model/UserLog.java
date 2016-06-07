package com.ans.cloud.data.model;

/**
 * 用户日志
 * Created by anzhen on 2015/12/23.
 */
public class UserLog extends BaseModel {

    // 请求id
    private String requestId;
    // 客户端ip
    private String chost;
    // 服务端ip
    private String shost;
    // 用户信息
    private String user;
    // 调用服务类型
    private String type;
    // 调用方法
    private String method;
    // 参数
    private String params;
    // 返回结果
    private String result;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getChost() {
        return chost;
    }

    public void setChost(String chost) {
        this.chost = chost;
    }

    public String getShost() {
        return shost;
    }

    public void setShost(String shost) {
        this.shost = shost;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
