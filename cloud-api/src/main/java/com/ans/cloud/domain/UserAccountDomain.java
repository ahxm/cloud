package com.ans.cloud.domain;


import com.ans.cloud.data.Addable;
import com.ans.cloud.data.Deletable;
import com.ans.cloud.data.Pageable;
import com.ans.cloud.data.Updatable;
import com.ans.cloud.data.exception.BusinessException;
import com.ans.cloud.data.exception.RepositoryException;
import com.ans.cloud.data.exception.UniqueException;
import com.ans.cloud.data.model.GroupUser;
import com.ans.cloud.data.model.UserAccount;
import com.ans.cloud.model.QUserAccount;

import java.util.List;

/**
 * 主子账号业务域
 * Created by anzhen on 2015/12/25.
 */
public interface UserAccountDomain extends Addable<UserAccount>, Deletable<UserAccount>, Updatable<UserAccount>, Pageable<UserAccount, QUserAccount> {

    /**
     * 添加用户
     *
     * @param user 子账号
     * @return
     * @throws BusinessException
     * @throws UniqueException
     * @throws RepositoryException
     */
    UserAccount addIfNotExists(UserAccount user) throws BusinessException, UniqueException, RepositoryException;

    /**
     * 根据主账号和用户PIN查找子账号
     *
     * @param account 主账号
     * @param pin     用户PIN
     * @return 子账号
     * @throws RepositoryException
     */
    UserAccount findByPin(String account, String pin) throws RepositoryException;

    /**
     * 根据用户PIN查找主子账号记录
     *
     * @param pin 用户PIN
     * @return 主子账号记录列表
     * @throws RepositoryException
     */
    List<UserAccount> findAccountByUserPin(String pin);

    /**
     * 获取主账户下的子账户数量
     *
     * @param account 主账户pin
     * @return 子账户数量(不包括自己）
     */
    int countByAccount(String account);

    List<UserAccount> findAddableUserAccountsByGroup(GroupUser groupUser);
}
