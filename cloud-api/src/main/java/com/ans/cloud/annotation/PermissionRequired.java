package com.ans.cloud.annotation;

import com.ans.cloud.data.model.PermissionType;

import java.lang.annotation.*;

/**
 * Created by anzhen on 2016/6/7.
 */
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionRequired {
    /**
     * 资源代码 @See com.jcloud.cap.data.model.Resource.getCode()
     *
     * @return
     */
    String resource() default "";

    /**
     * 必要的权限R/W
     *
     * @return 权限
     */
    PermissionType type() default PermissionType.READ;

    /**
     * 必须是主账户(管理员)登录才可以访问
     *
     * @return
     */
    boolean admin() default false;
}
