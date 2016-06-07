package com.ans.cloud.data;


import com.ans.cloud.data.exception.RepositoryException;

/**
 * 引用查询
 *
 * @param <M> 实体类型
 */
public interface Referencable<M> {

    /**
     * 判断实体对象是否被引用了
     *
     * @param model 实体对象
     * @return 被引用标示
     * @throws RepositoryException
     */
    boolean isRefered(M model) throws RepositoryException;

}