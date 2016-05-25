package registry.annotation;

import java.lang.annotation.*;

/**
 * Created by anzhen on 2016/5/25.
 */
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface DateBinding {

    /**
     * 键
     *
     * @return 键
     */
    String key() default "";

    /**
     * 默认值
     *
     * @return 默认值
     */
    String defaultValue() default "";

    /**
     * 是否可以为空
     *
     * @return 为空标示
     */
    boolean nullable() default false;

    /**
     * 日期格式
     *
     * @return 日期格式
     */
    String format() default "yyyy-MM-dd HH:mm:ss";
}
