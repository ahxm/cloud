package com.ans.cloud.data;



import com.ans.cloud.data.exception.RepositoryException;

import java.util.Date;

/**
 * 清理历史
 *
 * @param <M> 实体类型
 */
public interface Cleanable<M> {

    /**
     * 删除指定时间之前的历史数据
     *
     * @param time 指定时间
     * @return 影响的记录条数
     * @throws RepositoryException
     */
    int deleteBefore(Date time) throws RepositoryException;

}