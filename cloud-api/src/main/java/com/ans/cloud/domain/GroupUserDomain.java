package com.ans.cloud.domain;

import com.ans.cloud.data.*;
import com.ans.cloud.data.model.GroupUser;
import com.ans.cloud.data.model.UserAccount;
import com.ans.cloud.model.QGroupUser;

import java.util.List;

/**
 * Created by anzhen on 2016/6/7.
 */
public interface GroupUserDomain extends Addable<GroupUser>,
        Uniqueable<GroupUser>,
        Deletable<GroupUser>,
        Identifiable<GroupUser>,
        Pageable<GroupUser, QGroupUser> {

    /**
     * 通过account的pin和user的pin查找用户所加入的组
     *
     * @param groupUser 查询条件
     * @return
     */
    List<GroupUser> findByAccountAndPin(GroupUser groupUser);

    /**
     * 解除用户加入的所有的组关系
     *
     * @param userAccount
     */
    void deleteGroupUsers(UserAccount userAccount);
}
