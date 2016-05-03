package registry.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import registry.support.builder.ContextBuilder;
import registry.support.builder.IntervalKeyBuilder;
import registry.support.builder.KeyBuilder;
import registry.util.Context;
import registry.util.ServiceThread;

/**
 * Created by anzhen on 2016/5/3.
 */
public abstract class WorkerService extends LeaderService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 时间间隔
    protected long interval = 5000;
    // 工作线程
    protected Thread worker;
    // 配置定时间隔的key
    protected String defaultKey;
    // Key构建器
    protected KeyBuilder keyBuilder;
    // Key构建器互斥量
    protected Object keyBuilderMutex = new Object();
    // 上下文构建器
    protected ContextBuilder contextBuilder;

    public void setInterval(long interval) {
        if (interval > 0) {
            this.interval = interval;
        }
    }

    public void setDefaultKey(String defaultKey) {
        this.defaultKey = defaultKey;
    }

    public KeyBuilder getKeyBuilder() {
        if (keyBuilder == null) {
            synchronized (keyBuilderMutex) {
                if (keyBuilder == null) {
                    keyBuilder = createKeyBuilder();
                }
            }
        }
        return keyBuilder;
    }

    public void setKeyBuilder(KeyBuilder keyBuilder) {
        this.keyBuilder = keyBuilder;
    }

    public void setContextBuilder(ContextBuilder contextBuilder) {
        this.contextBuilder = contextBuilder;
    }

    protected KeyBuilder createKeyBuilder() {
        return new IntervalKeyBuilder(this.getClass());
    }

    /**
     * 创建上下文
     *
     * @return 上下文
     */
    protected Context createContext() {
        if (contextBuilder == null) {
            return null;
        }
        return contextBuilder.createContext();
    }

    /**
     * 时间间隔
     *
     * @param context 上下文
     * @return 时间间隔
     */
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

    /**
     * 获取初始化延迟时间
     *
     * @return 初始化延迟时间
     */
    protected long getInitialDelay() {
        return interval;
    }

    /**
     * 执行任务
     *
     * @param context 上下文
     * @throws Exception
     */
    protected abstract void execute(final Context context) throws Exception;

    @Override
    protected void onTakeLeader() {
        // 创建定时工作线程
        worker = new Thread(new ServiceThread(this, getInitialDelay()) {
            @Override
            protected boolean isAlive() {
                return leader.get() && super.isAlive();
            }

            @Override
            protected void execute() throws Exception {
                // 构建上下文
                Context context = createContext();
                try {
                    // 执行
                    WorkerService.this.execute(context);
                } finally {
                    this.interval = WorkerService.this.getInterval(context);
                }
            }

            @Override
            protected void onException(Throwable e) {
                logger.error("error occurs while executing worker.", e);
            }
        }, this.getClass().getSimpleName());
        worker.setDaemon(true);
        worker.start();
    }

    @Override
    protected void onLostLeader() {
        if (worker != null) {
            worker.interrupt();
        }
    }

}
