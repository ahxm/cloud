package registry.annotation;

import java.lang.annotation.*;

/**
 * Created by anzhen on 2016/5/1.
 */
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface IntRange {

    /**
     * 最小值
     *
     * @return 最小值
     */
    int min() default Integer.MIN_VALUE;

    /**
     * 最大值
     *
     * @return 最大值
     */
    int max() default Integer.MAX_VALUE;

    /**
     * 错误提示
     *
     * @return 消息
     */
    String message() default "";
}
