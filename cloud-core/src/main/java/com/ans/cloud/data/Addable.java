package com.ans.cloud.data;

import com.jcloud.cap.data.exception.BusinessException;
import com.jcloud.cap.data.exception.OptimisticLockException;
import com.jcloud.cap.data.exception.RepositoryException;
import com.jcloud.cap.data.exception.UniqueException;

/**
 * 增加
 *
 * @param <M> 实体类型
 */
public interface Addable<M> {
    /**
     * 增加实体
     *
     * @param model 实体对象
     * @return 实体对象
     * @throws BusinessException
     * @throws UniqueException
     * @throws RepositoryException
     * @throws OptimisticLockException
     */
    M add(M model) throws BusinessException, UniqueException, RepositoryException, OptimisticLockException;
}
