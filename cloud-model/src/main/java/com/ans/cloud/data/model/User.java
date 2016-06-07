package com.ans.cloud.data.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 主账号
 * Created by anzhen on 2015/12/25.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends BaseModel {
    // 普通状态
    public static final int STATUS_NORMAL = 1;
    // 初次登陆，完成引导页
    public static final int STATUS_SUPPLY = 2;
    // 欠费
    public static final int STATUS_ARREARAGE = 3;
    // 欠费停机
    public static final int STATUS_ARREARAGE_STOP = 4;
    // 欠费销毁
    public static final int STATUS_ARREARAGE_DESTROY = 5;

    // 中文名称
    protected String name;
    // 用户PING
    protected String pin;
    // 主账户
    private String account;
    // 邮件
    protected String mail;
    // 电话
    protected String phone;
    // 描述
    protected String description;
    // 用户类型
    protected int userType;
    //active状态
    protected int activeStatus;
    //客服电话
    private String cscPhone;
    //客服email
    private String cscEmail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAccount() {
        if (account == null || account.isEmpty()) {
            return pin;
        }
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(int activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getCscPhone() {
        return cscPhone;
    }

    public void setCscPhone(String cscPhone) {
        this.cscPhone = cscPhone;
    }

    public String getCscEmail() {
        return cscEmail;
    }

    public void setCscEmail(String cscEmail) {
        this.cscEmail = cscEmail;
    }
}
