package com.ans.cloud.model;

/**
 * Created by anzhen on 2016/4/15.
 */
public interface PageRequest {
    /**
     * 当前页
     * @return
     */
    int getPageNumber();

    /**
     * 每页页数
     * @return
     */
    int getPageSize();
}
