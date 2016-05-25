package com.ans.cloud.data.model;

/**
 * 分页查询条件
 */
public class QPageQuery<Q extends Query> implements Query {
    // 分页
    private Pagination pagination;
    // 查询
    private Q query;

    public QPageQuery() {
    }

    public QPageQuery(Pagination pagination, Q query) {
        this.pagination = pagination;
        this.query = query;
    }

    public Pagination getPagination() {
        return this.pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Q getQuery() {
        return query;
    }

    public void setQuery(Q query) {
        this.query = query;
    }
}