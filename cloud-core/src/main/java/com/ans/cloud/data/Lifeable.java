package com.ans.cloud.data;

import com.jcloud.cap.data.exception.RepositoryException;

/**
 * 启用禁用管理
 * Created by anzhen on 15-3-2.
 */
public interface Lifeable<M> {
    /**
     * 启用
     *
     * @param model 实体对象
     * @return 影响的条数
     * @throws RepositoryException
     */
    int enable(M model) throws RepositoryException;

    /**
     * 禁用
     *
     * @param model 实体对象
     * @return 影响的条数
     * @throws RepositoryException
     */
    int disable(M model) throws RepositoryException;
}
