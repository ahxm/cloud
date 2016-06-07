package com.ans.cloud.data.model;

/**
 * 会话相关查询，查询当前用户能访问的数据
 *
 * @author anzhen
 * @since 2015-07-07 11:32
 */
public abstract class QSession implements Query {
    // 角色
    protected int role;
    // 用户ID
    protected long userId;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
