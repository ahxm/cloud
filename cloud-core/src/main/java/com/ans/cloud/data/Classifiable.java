package com.ans.cloud.data;

import com.jcloud.cap.data.exception.RepositoryException;

import java.util.List;

/**
 * 分类查询
 *
 * @param <M> 实体类型
 */
public interface Classifiable<M> {

    /**
     * 根据类型查找实体
     *
     * @param type 类型
     * @return 匹配的实体列表
     * @throws RepositoryException
     */
    List<M> findByType(String type) throws RepositoryException;
}