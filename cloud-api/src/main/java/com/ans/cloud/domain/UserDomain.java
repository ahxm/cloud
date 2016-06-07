package com.ans.cloud.domain;


import com.ans.cloud.data.Addable;
import com.ans.cloud.data.Codeable;
import com.ans.cloud.data.Identifiable;
import com.ans.cloud.data.Stateable;
import com.ans.cloud.data.model.User;

/**
 * 用户业务域
 * Created by anzhen on 2015/12/25.
 */
public interface UserDomain extends Addable<User>, Codeable<User>, Stateable<User>, Identifiable<User> {

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    User addIfNotExists(User user);

}
