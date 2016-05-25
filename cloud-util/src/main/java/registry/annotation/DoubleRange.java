package registry.annotation;

import java.lang.annotation.*;

/**
 * Created by anzhen on 2016/5/25.
 */
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleRange {

    /**
     * 最小值
     *
     * @return 最小值
     */
    double min() default Double.MIN_VALUE;

    /**
     * 最大值
     *
     * @return 最大值
     */
    double max() default Double.MAX_VALUE;

    /**
     * 错误提示
     *
     * @return 消息
     */
    String message() default "";

}
