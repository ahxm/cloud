package com.ans.cloud.domain;

import com.ans.cloud.data.*;
import com.ans.cloud.model.Group;
import com.ans.cloud.model.QGroup;

/**
 * Created by anzhen on 2016/6/7.
 */
public interface GroupDomain extends Addable<Group>, Deletable<Group>, Updatable<Group>, Identifiable<Group>, Pageable<Group, QGroup>, Referencable<Group> {
}
