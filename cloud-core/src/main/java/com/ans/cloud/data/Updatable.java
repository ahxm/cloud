package com.ans.cloud.data;


import com.ans.cloud.data.exception.BusinessException;
import com.ans.cloud.data.exception.NotFoundException;
import com.ans.cloud.data.exception.OptimisticLockException;
import com.ans.cloud.data.exception.RepositoryException;

/**
 * 修改
 *
 * @param <M> 实体类型
 */
public interface Updatable<M> extends Identifiable<M> {

    /**
     * 修改实体
     *
     * @param model 实体对象
     * @return 影响的记录条数
     * @throws BusinessException
     * @throws NotFoundException
     * @throws RepositoryException
     * @throws OptimisticLockException
     */
    int update(M model) throws BusinessException, NotFoundException, RepositoryException, OptimisticLockException;
}