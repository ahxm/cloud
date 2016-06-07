package com.ans.cloud.domain;


import com.ans.cloud.data.*;
import com.ans.cloud.data.model.UserAccount;
import com.ans.cloud.data.model.UserPermission;
import com.ans.cloud.model.QUserPermission;

/**
 * Created by anzhen on 2016/2/25.
 */
public interface UserPermissionDomain extends Addable<UserPermission>,
        Uniqueable<UserPermission>,
        Deletable<UserPermission>,
        Identifiable<UserPermission>,
        Pageable<UserPermission, QUserPermission> {

    /**
     * 删除对子用户的所有直接授权
     *
     * @param userAccount
     */
    void deleteUserPermission(UserAccount userAccount);

    /**
     * 根据account和permissionId清除授权策略对应的用户授权策略关系
     *
     * @param userPermission
     */
    void clearByPermission(UserPermission userPermission);

    /**
     * 返回授权策略的用户引用数
     *
     * @param userPermission 授权策略id+account id
     * @return
     */
    int findUsedCount(UserPermission userPermission);
}
