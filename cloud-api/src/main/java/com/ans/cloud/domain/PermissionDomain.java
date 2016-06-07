package com.ans.cloud.domain;

import com.ans.cloud.data.Addable;
import com.ans.cloud.data.Deletable;
import com.ans.cloud.data.Pageable;
import com.ans.cloud.data.Updatable;
import com.ans.cloud.data.model.Permission;
import com.ans.cloud.model.QGroupPermission;
import com.ans.cloud.model.QPermission;
import com.ans.cloud.model.QUserPermission;

import java.util.List;

/**
 * Created by anzhen on 2016/6/7.
 */
public interface PermissionDomain  extends Addable<Permission>, Deletable<Permission>, Updatable<Permission>, Pageable<Permission, QPermission> {

    /**
     * 查询主账户下的自定义访问策略数量
     *
     * @param account 主账户pin
     * @return 数量
     */
    int countByAccount(String account);

    /**
     * 查询可以对用户进行授权的列表
     *
     * @param userPermission 条件
     * @return 列表
     */
    List<Permission> findAddablePermissionsOfSubUser(QUserPermission userPermission);

    /**
     * 查询可以对组进行授权的列表
     *
     * @param qGroupPermission 条件
     * @return 列表
     */
    List<Permission> findAddablePermissionsOfGroup(QGroupPermission qGroupPermission);

    /**
     * 根据名字查询账户下的权限定义，如果是系统默认权限名也会返回
     *
     * @param account 主账户
     * @param name    权限定义名
     * @return 权限
     */
    Permission findByName(String account, String name);
}
