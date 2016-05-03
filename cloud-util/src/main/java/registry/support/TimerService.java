package registry.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import registry.support.builder.ContextBuilder;
import registry.support.builder.IntervalKeyBuilder;
import registry.support.builder.KeyBuilder;
import registry.util.Context;
import registry.util.Service;
import registry.util.ServiceThread;

/**
 * Created by anzhen on 2016/5/3.
 */
public abstract class TimerService extends Service {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected long interval = 5000;

    protected String defaultKey;

    protected KeyBuilder keyBuilder;

    protected ContextBuilder contextBuilder;

    protected Thread thread;

    public void setInterval(long interval) {
        if (interval > 0) {
            this.interval = interval;
        }
    }

    public void setDefaultKey(String defaultKey) {
        this.defaultKey = defaultKey;
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        thread = new Thread(new ServiceThread(this,getInitialDelay()){
            /**
             * 执行任务
             *
             * @throws Exception
             */
            @Override
            protected void execute() throws Exception {
                // 构建上下文
                Context context = createContext();
                try {
                    // 执行
                   TimerService.this.execute(context);
                } finally {
                    // 下次执行时间
                    this.interval = TimerService.this.getInterval(context);
                }

            }

            /**
             * 出现异常
             *
             * @param e 异常
             */
            @Override
            protected void onException(Throwable e) {
                logger.error("error occurs while executing worker.", e);
            }
        },this.getClass().getSimpleName());
        thread.setDaemon(true);
        thread.start();

    }

    @Override
    protected void doStop() {
        if (thread != null) {
            thread.interrupt();
        }
        super.doStop();
    }

    protected long getInitialDelay() {
        return interval;
    }

    public KeyBuilder getKeyBuilder() {
        if (keyBuilder == null) {
            rwLock.readLock().lock();
            try {
                if (keyBuilder == null) {
                    keyBuilder = createKeyBuilder();
                }
            } finally {
                rwLock.readLock().unlock();
            }
        }
        return keyBuilder;
    }

    /**
     * 执行任务
     *
     * @param context 上下文
     * @throws Exception
     */
    protected abstract void execute(final Context context) throws Exception;

    protected KeyBuilder createKeyBuilder() {
        return new IntervalKeyBuilder(this.getClass());
    }

    public void setContextBuilder(ContextBuilder contextBuilder) {
        this.contextBuilder = contextBuilder;
    }

    protected Context createContext(){
        if(contextBuilder == null){
            return null;
        }
        return contextBuilder.createContext();
    }

    protected long getInterval(final Context context) {
        if (context == null) {
            return interval;
        }
        String key = defaultKey == null || defaultKey.isEmpty() ? getKeyBuilder().getKey() : defaultKey;
        if (key == null || key.isEmpty()) {
            return interval;
        }
        return context.getParameter(key, interval);
    }

    public String getDefaultKey() {
        return defaultKey;
    }

    public ContextBuilder getContextBuilder() {
        return contextBuilder;
    }
}
