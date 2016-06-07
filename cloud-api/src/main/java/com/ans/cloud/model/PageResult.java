package com.ans.cloud.model;

import com.ans.cloud.data.model.Pagination;

import java.util.List;

/**
 * 对外提供服务的分页类
 *
 * @autor liuxiangdong1
 * @date 2015/11/20
 */
public class PageResult<T> {
    private List<T> result;
    private Pagination pagination;

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "result=" + result +
                ", pagination=" + pagination +
                '}';
    }
}
