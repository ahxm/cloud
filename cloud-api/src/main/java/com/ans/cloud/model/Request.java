package com.ans.cloud.model;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author anzhen
 * @since 2015-12-29 15:11
 */
public class Request implements Serializable, Cloneable {
    public static final int G_MB = 1024;
    public static final String UTF_8 = "UTF-8";
    public static final Charset CHARSET_UTF8 = Charset.forName(UTF_8);
    public static final String JSON = "json";
    public static final String XML = "xml";
    public static final String VERSION_1 = "v1";
    public static final String SIGN_VERSION_1 = "v1";
    public static final String HMAC_SHA1 = "HMAC-SHA1";
    public static final String P_ACTION = "action";
    public static final String P_REQUEST_ID = "requestId";
    public static final String P_ACCEPT_TYPE = "acceptType";
    public static final String P_ACCESS_KEY = "accessKey";
    public static final String P_SIGNATURE = "signature";
    public static final String P_MODULE = "module";
    public static final String P_SIGNATURE_METHOD = "signatureMethod";
    public static final String P_SIGNATURE_VERSION = "signatureVersion";
    public static final String P_API_VERSION = "apiVersion";
    public static final String P_TIMESTAMP = "timestamp";

    public static final String CONFUSE_CHARACTERS = "xxxxxxx";
    // 客户端唯一请求ID
    protected String requestId;
    // 时间戳
    protected Date timestamp;
    // 签名方法
    protected String signatureMethod;
    // 签名版本
    protected String signatureVersion;
    // 签名
    protected String signature;
    // 访问的Key
    protected String accessKey;
    // 主账号用户名
    protected String account;
    // 当前调用用户
    protected String user;
    // API版本
    protected String apiVersion;
    // 接受的格式
    protected String acceptType;
    // 数据中心
    protected String dataCenter;
    // 业务模块
    protected String module;
    // 请求方法
    protected String action;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignatureMethod() {
        return signatureMethod;
    }

    public void setSignatureMethod(String signatureMethod) {
        this.signatureMethod = signatureMethod;
    }

    public String getSignatureVersion() {
        return signatureVersion;
    }

    public void setSignatureVersion(String signatureVersion) {
        this.signatureVersion = signatureVersion;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getAcceptType() {
        return acceptType;
    }

    public void setAcceptType(String acceptType) {
        this.acceptType = acceptType;
    }

    public String getDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public Request clone() {
        try {
            return (Request) super.clone();
        } catch (CloneNotSupportedException ignored) {
            return null;
        }
    }

    /**
     * 混淆器
     */
    public void confuse() {
        this.accessKey = CONFUSE_CHARACTERS;
    }

    /**
     * 拷贝信息
     *
     * @param request
     */
    public void copy(Request request) {
        if (request == null) {
            return;
        }
        this.setAccount(request.getAccount());
        this.setUser(request.getUser());
        this.setModule(request.getModule());
        this.setDataCenter(request.getDataCenter());
    }

}
