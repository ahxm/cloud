package registry.annotation;

import java.lang.annotation.*;

/**
 * Created by anzhen on 2016/5/25.
 */
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ShortBinding {

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
    short defaultValue() default (short) 0;

    /**
     * 数值范围
     *
     * @return 数值范围
     */
    ShortRange range() default @ShortRange;

}
