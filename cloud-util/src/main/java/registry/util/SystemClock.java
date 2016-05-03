package registry.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by anzhen on 2016/5/3.
 */
public class SystemClock {

    private static final SystemClock instance = new SystemClock();
    private final long precision;
    private final AtomicLong now;
    private ScheduledExecutorService  scheduledExecutorService;

    public static SystemClock getInstance() {
        return instance;
    }

    public SystemClock() {
        this(1L);
    }

    public long now() {
        return now.get();
    }

    public long precision() {
        return precision;
    }

    public SystemClock(long precision) {
        this.precision = precision;
        now = new AtomicLong(System.currentTimeMillis());
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r,"System_Clock");
                thread.setDaemon(true);
                return thread;
            }
        });

        scheduledExecutorService.scheduleAtFixedRate(new Timer(now),precision,precision, TimeUnit.MILLISECONDS);
    }

    protected class Timer implements Runnable{

        private final AtomicLong now;

        public Timer(AtomicLong now) {
            this.now = now;
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p/>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
           now.set(System.currentTimeMillis());
        }
    }
}
