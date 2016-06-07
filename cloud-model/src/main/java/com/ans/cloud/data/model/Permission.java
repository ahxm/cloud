package com.ans.cloud.data.model;

/**
 * Created by anzhen on 2016/6/7.
 */
public class Permission extends BaseModel {

    //1：系统权限
    public static final int TYPE_SYS = 1;
    //2：私有权限
    public static final int TYPE_PRIVATE = 2;
    //策略名字
    private String name;
    //主账户pin
    private String account;
    //策略备注
    private String description;
    //策略内容
    private String content;
    //策略类型1:系统；2：自定义
    private int type = TYPE_PRIVATE;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isSystemPermission() {
        return this.type == 1;
    }
}
