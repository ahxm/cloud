package com.ans.cloud.domain;

import com.ans.cloud.data.Addable;
import com.ans.cloud.data.Identifiable;
import com.ans.cloud.data.Listable;
import com.ans.cloud.data.exception.RepositoryException;
import com.ans.cloud.data.model.Cluster;

/**
 * 集群业务域
 * Created by anzhen on 2016/6/7.
 */
public interface ClusterDomain extends Addable<Cluster>, Identifiable<Cluster>, Listable<Cluster> {
    /**
     * 根据数据中心和type查询
     *
     * @param dataCenter 数据中心
     * @param type       类型
     * @return 集群
     */
    Cluster findByType(String dataCenter, String type) throws RepositoryException;

}
