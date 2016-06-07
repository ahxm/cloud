package com.ans.cloud.domain;

import com.ans.cloud.data.Addable;
import com.ans.cloud.data.Pageable;
import com.ans.cloud.data.model.PermissionLog;
import com.ans.cloud.model.QPermissionLog;


/**
 * Created by anzhen on 2016/2/24.
 */
public interface PermissionLogDomain extends Addable<PermissionLog >,Pageable<PermissionLog,QPermissionLog> {
}
