package com.ans.cloud.annotation;

import java.lang.annotation.*;

/**
 * Created by anzhen on 2016/5/31.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExposeScope {

    Scope value() default Scope.WEB;

    enum Scope{
        INNER,WEB,API
    }
}
