package registry.plugin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 共享插件标识
 * Created by anzhen on 2016/5/25.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Share {

    public ShareKey shareKey() default ShareKey.ALL;

    public String[] excludes() default {};

    public enum ShareKey {
        ALL, ADDRESS
    }

}
