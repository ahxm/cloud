package com.ans.cloud.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果
 *
 * @param <M> 实体对象类型
 */
public class Page<M> implements Serializable {

    // 分页
    private Pagination pagination;
    // 结果列表
    private List<M> result;

    public Page() {
    }

    public Page(Pagination pagination, List<M> result) {
        this.pagination = pagination;
        this.result = result;
    }

    public Pagination getPagination() {
        return this.pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<M> getResult() {
        return this.result;
    }

    public void setResult(List<M> result) {
        this.result = result;
    }

    /**
     * 是否还有下一页数据
     *
     * @return 下一页数据标示
     */
    public boolean hasNext() {
        if (result == null || result.size() < pagination.getPageSize()) {
            return false;
        }
        return pagination.getPage() < pagination.getTotalPage();
    }

}