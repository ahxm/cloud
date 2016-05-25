package com.ans.cloud.data;

import com.jcloud.cap.data.exception.BusinessException;
import com.jcloud.cap.data.exception.RepositoryException;
import com.jcloud.cap.data.model.Transition;

import java.util.List;

/**
 * 状态变化和查询
 *
 * @param <M> 实体类型
 */
public interface Stateable<M> {

    /**
     * 状态变更,从指定状态变化到新状态
     *
     * @param transition 变更对象
     * @return 影响的记录条数
     * @throws RepositoryException
     */
    int transit(Transition<M> transition) throws BusinessException, RepositoryException;

    /**
     * 变更状态
     *
     * @param model 实体对象
     * @return 影响的记录条数
     * @throws RepositoryException
     */
    int state(M model) throws RepositoryException;

    /**
     * 根据状态进行查询
     *
     * @param states 状态数组
     * @return 实体列表
     * @throws RepositoryException
     */
    List<M> findByState(int... states) throws RepositoryException;

}