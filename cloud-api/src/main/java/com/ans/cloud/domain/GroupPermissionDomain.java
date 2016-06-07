package com.ans.cloud.domain;

import com.ans.cloud.data.*;
import com.ans.cloud.data.model.GroupPermission;
import com.ans.cloud.model.QGroupPermission;

import java.util.List;

/**
 * Created by anzhen on 2016/6/7.
 */
public interface GroupPermissionDomain extends Addable<GroupPermission>,
        Uniqueable<GroupPermission>,
        Deletable<GroupPermission>,
        Identifiable<GroupPermission>,
        Pageable<GroupPermission, QGroupPermission> {

    /**
     * 获取某个组被授予的所有的权限ID,包含系统权限
     *
     * @param groupId 组id
     * @return 用户组对权限的关系列表
     */
    List<GroupPermission> findByGroupId(long groupId);

    /**
     * 清除授权策略对应的组授权策略关系
     *
     * @param permissionId
     */
    void deleteByPermission(long permissionId);

    /**
     * 返回授权策略的组引用数
     *
     * @param groupPermission 授权策略id+account id
     * @return
     */
    int countUsed(GroupPermission groupPermission);

}
