package com.ans.cloud.data;


import com.ans.cloud.data.exception.RepositoryException;

/**
 * 唯一性校验仓库
 *
 * @param <M> 实体对象类型
 */
public interface Uniqueable<M> {

    /**
     * 是否存在
     *
     * @param model 实体对象
     * @return 存在标示
     * @throws RepositoryException
     */
    M exists(M model) throws RepositoryException;

}