package registry.annotation;

import java.lang.annotation.*;

/**
 * Created by anzhen on 2016/5/1.
 */
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

    String message() default "";

}
