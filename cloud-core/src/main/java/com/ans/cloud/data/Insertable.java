package com.ans.cloud.data;

import com.jcloud.cap.data.exception.DuplicateKeyException;
import com.jcloud.cap.data.exception.RepositoryException;

/**
 * 增加
 *
 * @param <M> 实体类型
 */
public interface Insertable<M> {

    /**
     * 增加实体
     *
     * @param model 实体对象
     * @return 受影响的行数
     * @throws RepositoryException
     * @throws DuplicateKeyException
     */
    int add(M model) throws RepositoryException, DuplicateKeyException;
}