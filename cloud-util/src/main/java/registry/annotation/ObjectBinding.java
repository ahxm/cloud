package registry.annotation;

import java.lang.annotation.*;

/**
 * Created by anzhen on 2016/5/25.
 */
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectBinding {

    /**
     * 键
     *
     * @return 键
     */
    String key() default "";

    /**
     * 是否可以为空
     *
     * @return 为空标示
     */
    boolean nullable() default false;

}
