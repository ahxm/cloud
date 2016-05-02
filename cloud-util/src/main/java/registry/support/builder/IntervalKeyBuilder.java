package registry.support.builder;

/**
 * Created by anzhen on 2016/5/2.
 */
public class IntervalKeyBuilder extends JobKeyBuilder {

    public IntervalKeyBuilder(Class<?> jobClazz) {
        super(jobClazz, ".interval");
    }

}
