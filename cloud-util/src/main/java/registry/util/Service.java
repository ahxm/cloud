package registry.util;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by anzhen on 2016/4/30.
 */
public abstract class Service implements LifeCycle {

    protected final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    protected final Lock mutex = rwLock.writeLock();

    protected final AtomicBoolean started = new AtomicBoolean(false);

    protected final AtomicReference<ServiceState> serviceState = new AtomicReference<ServiceState>();

    protected final Object signal = new Object();


    @Override
    public void start() throws Exception {

        validate();
        serviceState.set(ServiceState.WILL_START);
        beforeStart();
        mutex.lock();
        try {
            if (started.compareAndSet(false, true)) {
                serviceState.set(ServiceState.STARTING);
                doStart();
                afterStart();
                serviceState.set(ServiceState.STARTED);
            }
        } catch (Exception e) {
            serviceState.set(ServiceState.START_FAILED);
            startError(e);
            stop();
            Exception ex = convert(e);
            if (ex != null) {
                throw ex;
            }
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void stop() {

        serviceState.set(ServiceState.WILL_STOP);
        synchronized (signal) {
            signal.notifyAll();
        }
        beforeStop();
        mutex.lock();
        try {
            if (started.compareAndSet(true, false)) {
                serviceState.set(ServiceState.STOPPING);
                doStop();
                afterStop();
                serviceState.set(ServiceState.STOPPED);
            }
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public boolean isStarted() {
        if (started.get()) {
            switch (serviceState.get()) {
                case WILL_STOP:
                    return false;
                case STOPPING:
                    return false;
                case STOPPED:
                    return false;
                default:
                    return true;
            }
        }
        return false;
    }

    protected void beforeStart() throws Exception {
    }

    protected void validate()throws Exception  {
        Validators.validate(this);
    }

    protected void doStart() throws Exception {
    }

    protected void afterStart() throws Exception {
    }

    protected void startError(Exception e) {
    }

    protected Exception convert(final Exception e) {
        return e;
    }

    protected void beforeStop() {
    }

    protected void doStop() {
    }

    protected void afterStop() {
    }

    public void willStop() {
        serviceState.set(ServiceState.WILL_STOP);
        beforeStop();
    }

    public boolean isStopped() {
        switch (serviceState.get()) {
            case START_FAILED:
                return true;
            case WILL_STOP:
                return true;
            case STOPPING:
                return true;
            case STOPPED:
                return true;
            default:
                return false;
        }
    }

    /**
     * 等待一段时间，如果服务已经关闭则立即返回
     *
     * @param time 时间
     */
    protected void await(final long time) {
        if (!isStarted()) {
            return;
        }
        synchronized (signal) {
            try {
                signal.wait(time);
            } catch (InterruptedException e) {
                // 当前线程终止
                Thread.currentThread().interrupt();
            }
        }
    }

    public ServiceState getServiceState() {
        return serviceState.get();
    }

    /**
     * 服务状态
     */
    public static enum ServiceState {
        /**
         * 准备启动
         */
        WILL_START,
        /**
         * 启动中
         */
        STARTING,
        /**
         * 启动失败
         */
        START_FAILED,
        /**
         * 启动完成
         */
        STARTED,
        /**
         * 准备关闭
         */
        WILL_STOP,
        /**
         * 关闭中
         */
        STOPPING,
        /**
         * 关闭完成
         */
        STOPPED
    }

}
