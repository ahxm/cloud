package com.ans.cloud.data;


import com.ans.cloud.data.exception.BusinessException;
import com.ans.cloud.data.exception.RepositoryException;

/**
 * 删除
 *
 * @param <M> 实体类型
 */
public interface Deletable<M> {

    /**
     * 删除实体
     *
     * @param model 实体对象
     * @return 影响的记录条数
     * @throws RepositoryException
     */
    int delete(M model) throws BusinessException, RepositoryException;

}