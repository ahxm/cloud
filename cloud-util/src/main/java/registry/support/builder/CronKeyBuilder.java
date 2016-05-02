package registry.support.builder;

/**
 * Created by anzhen on 2016/5/2.
 */
public class CronKeyBuilder extends JobKeyBuilder {

    public CronKeyBuilder(Class<?> jobClass){super(jobClass,".cron");}

}
