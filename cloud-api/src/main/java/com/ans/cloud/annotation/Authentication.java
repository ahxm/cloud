package com.ans.cloud.annotation;

import java.lang.annotation.*;

/**
 * 是否需要用户认证
 * Created by anzhen on 2016/5/31.
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Authentication {

    boolean enable() default true;

}
