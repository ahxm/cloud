package com.ans.cloud.data.model;

/**
 * 关键字查询
 */
public class QKeyword implements Query {

    // 关键字
    protected String keyword;

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public QKeyword() {
    }

    public QKeyword(String keyword) {
        this.keyword = keyword;
    }
}