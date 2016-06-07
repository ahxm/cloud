package com.ans.cloud.annotation;

import com.ans.cloud.data.model.ActionType;
import com.ans.cloud.data.model.Resource;
import java.lang.annotation.*;

/**
 * Created by anzhen on 2016/6/7.
 */
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Quota {


    /**
     * 服务代码
     *
     * @return
     */
    Resource resource();

    /**
     * 操作类型
     *
     * @return 操作类型
     */
    ActionType action();
}
