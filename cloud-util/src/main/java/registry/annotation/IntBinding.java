package registry.annotation;

import java.lang.annotation.*;

/**
 * Created by anzhen on 2016/5/25.
 */
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface IntBinding {

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
    int defaultValue() default 0;

    /**
     * 数值范围
     *
     * @return 数值范围
     */
    IntRange range() default @IntRange;

}
