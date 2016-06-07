package com.ans.cloud.service;

import com.ans.cloud.data.*;
import com.ans.cloud.data.exception.RepositoryException;
import com.ans.cloud.data.model.ConfigInfo;
import com.ans.cloud.data.model.QKeyword;

/**
 * Created by anzhen on 2016/6/7.
 */
public interface ConfigService  extends Addable<ConfigInfo>, Updatable<ConfigInfo>, Deletable<ConfigInfo>, Classifiable<ConfigInfo>,
        Pageable<ConfigInfo, QKeyword> {

    /**
     * 根据Key查找
     *
     * @param group 分组
     * @param key   键
     * @return 配置
     * @throws RepositoryException
     */
    ConfigInfo findByKey(String group, String key) throws RepositoryException;
}
