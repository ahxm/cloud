package registry.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import registry.support.builder.CronKeyBuilder;
import registry.support.builder.KeyBuilder;
import registry.util.Context;
import registry.util.CronExpression;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by anzhen on 2016/5/3.
 */
public abstract class CronTimerService extends TimerService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String cron;

    protected Map<String,CronExpression> cronExpressions = new ConcurrentHashMap<String, CronExpression>();


    public void setCron(String cron) {
        this.cron = cron;
    }

    @Override
    protected KeyBuilder createKeyBuilder() {
        return new CronKeyBuilder(this.getClass());
    }

    /**
     * 获取Cron表达式
     *
     * @param context 上下文
     * @return Cron表达式
     */
    protected String getCronExpress(final Context context) {
        if (context == null) {
            return cron;
        }
        String key = defaultKey == null || defaultKey.isEmpty() ? getKeyBuilder().getKey() : defaultKey;
        if (key == null || key.isEmpty()) {
            return cron;
        }
        return context.getParameter(key, cron);
    }

    @Override
    protected long getInterval(final Context context) {
        // 获取表达式字符串
        String cron = getCronExpress(context);
        try {
            // 获取表达式对象
            CronExpression expression = cronExpressions.get(cron);
            if (expression == null) {
                expression = new CronExpression(cron);
                cronExpressions.put(cron, expression);
            }
            // 获取下一个时间间隔
            Date now = new Date();
            Date next = expression.getNextValidTimeAfter(now);
            return next.getTime() - now.getTime();
        } catch (Exception e) {
            logger.error(String.format("invalid cron expression [%s].", cron), e);
            return interval;
        }
    }

    @Override
    protected long getInitialDelay() {
        try {
            Context context = createContext();
            return getInterval(context);
        } catch (Exception e) {
            logger.error("get initial delay error.", e);
            return interval;
        }
    }

}
