package com.ans.cloud.data;


import com.ans.cloud.data.exception.BusinessException;
import com.ans.cloud.data.exception.NotFoundException;
import com.ans.cloud.data.exception.RepositoryException;

/**
 * 审批接口
 * Created by anzhen on 15-3-6.
 */
public interface Auditable {

    /**
     * 通过
     *
     * @param id      对象ID
     * @param comment 意见
     * @throws BusinessException
     * @throws NotFoundException
     * @throws RepositoryException
     */
    void pass(long id, String comment) throws BusinessException, NotFoundException, RepositoryException;

    /**
     * 拒绝
     *
     * @param id      对象ID
     * @param comment 意见
     * @throws BusinessException
     * @throws NotFoundException
     * @throws RepositoryException
     */
    void refuse(long id, String comment) throws BusinessException, NotFoundException, RepositoryException;
}
