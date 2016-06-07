package com.ans.cloud.model;

/**
 * Created by anzhen on 2016/1/5.
 */
public enum ResponseStatus {

    OK(200, "OK"),
    /**
     * 参数无效
     */
    Invalid(400, "Invalid.Parameter"),
    /**
     * 路径无效
     */
    InvalidPath(400, "Invalid.Path"),
    /**
     * 重复提交
     */
    RepeatRequest(400, "RepeatRequest"),
    /**
     * 没有授权
     */
    Unauthorized(401, "Unauthorized"),
    /**
     * 欠费
     */
    Arrearage(403, "Arrearage"),
    /**
     * 超过配额
     */
    OverAge(403, "OverAge"),
    /**
     * 禁止访问
     */
    Forbidden(403, "Forbidden"),
    /**
     * 资源没有找到
     */
    NotFound(404, "NotFound"),
    /**
     * 请求超时
     */
    Timeout(408, "Timeout"),
    /**
     * 冲突
     */
    Conflict(409, "Conflict"),
    /**
     * 内部错误
     */
    InternalError(510, "InternalError"),
    /**
     * 资源被引用，不能操作
     */
    ReferenceError(409, "ReferenceError"),
    /*
     * 内部错误，不可操作
     */
    NotOperational(409, "NotOperational"),

    /**
     * 内部可以忽略错误
     */
    IgnoreError(599, "IgnoreError");

    // 代码
    private String code;
    // 状态
    private int status;

    ResponseStatus(int status, String code) {
        this.status = status;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
