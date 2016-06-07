package com.ans.cloud.domain;

import com.ans.cloud.data.Addable;
import com.ans.cloud.data.Identifiable;
import com.ans.cloud.data.exception.BusinessException;
import com.ans.cloud.data.exception.OptimisticLockException;
import com.ans.cloud.data.exception.RepositoryException;
import com.ans.cloud.data.exception.UniqueException;
import com.ans.cloud.data.model.ClusterUser;

/**
 * Created by anzhen on 2016/6/7.
 */
public interface ClusterUserDomain extends Addable<ClusterUser>, Identifiable<ClusterUser> {

    /**
     * 查找用户
     *
     * @param user      用户
     * @param clusterId 集群ID
     * @return 集群用户
     */
    ClusterUser findByUser(String user, long clusterId) throws RepositoryException;

    /**
     * 查找用户
     *
     * @param tenantId  租户ID
     * @param clusterId 集群ID
     * @return 集群用户
     */
    ClusterUser findByTenant(String tenantId, long clusterId) throws RepositoryException;

    /**
     * 添加用户
     *
     * @param user 集群用户
     * @return 集群用户
     */
    ClusterUser addIfNotExists(ClusterUser user) throws BusinessException, UniqueException, RepositoryException,
            OptimisticLockException;
}
