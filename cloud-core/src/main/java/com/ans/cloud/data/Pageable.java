package com.ans.cloud.data;


import com.ans.cloud.data.exception.RepositoryException;
import com.ans.cloud.data.model.Page;
import com.ans.cloud.data.model.QPageQuery;
import com.ans.cloud.data.model.Query;

/**
 * 分页查找对象
 *
 * @param <M> 实体类型
 */
public interface Pageable<M, Q extends Query> {

    /**
     * 分页查询
     *
     * @param query 分页查询条件
     * @return 分页数据
     * @throws RepositoryException
     */
    Page<M> findByQuery(QPageQuery<Q> query) throws RepositoryException;

}