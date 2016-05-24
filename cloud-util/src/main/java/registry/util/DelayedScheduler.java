package registry.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by anzhen on 2016/5/24.
 */
public class DelayedScheduler extends Service {

    private int threads;

    private ScheduledExecutorService scheduler;

    public DelayedScheduler(int threads) {
        if (threads <= 0) {
            throw new IllegalArgumentException("threads must be greater than 0");
        }
        this.threads = threads;
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        scheduler = Executors.newScheduledThreadPool(threads, new NamedThreadFactory("DelayedScheduler"));
    }

    @Override
    protected void doStop() {
        Closeables.close(scheduler);
        super.doStop();
    }

    public void schedule(final Runnable command, final long delay) {
        if (command != null) {
            scheduler.schedule(command, delay, TimeUnit.MILLISECONDS);
        }
    }

    public void scheduleWithFixedDelay(final Runnable command, final long initialDelay, final long delay) {

        if (command != null) {
            scheduler.scheduleWithFixedDelay(command, initialDelay, delay, TimeUnit.MILLISECONDS);
        }
    }

    public void scheduleAtFixedRate(final Runnable command, final long initialDelay, final long period) {
        if (command != null) {
            scheduler.scheduleAtFixedRate(command, initialDelay, period, TimeUnit.MILLISECONDS);
        }
    }


    public void close(final LifeCycle service, final long delay) {
        if (service != null) {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    service.stop();
                }
            }, delay, TimeUnit.MILLISECONDS);
        }
    }
}
