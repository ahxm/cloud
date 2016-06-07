package com.ans.cloud.model;

/**
 * 授权信息，name可以是用户名，也可以是数据库名，要看调用的方式
 * Created by anzhen on 2016/4/14.
 */
public class AuthInfo {
    public static final String RO = "ro";
    public static final String RW = "rw";

    private String name;

    private String readwrite;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReadwrite() {
        return readwrite;
    }

    public void setReadwrite(String readwrite) {
        this.readwrite = readwrite;
    }
}
