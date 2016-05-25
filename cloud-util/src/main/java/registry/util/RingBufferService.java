package registry.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by anzhen on 2016/5/25.
 */
public class RingBufferService implements LifeCycle{

    // 等待处理完
    protected CountDownLatch latch = new CountDownLatch(1);
    // 派发线程
    protected Thread dispatchThread;
    // 最大容量
    protected int capacity;
    // 处理器
    protected EventHandler handler;
    protected RingBuffer ringBuffer;
    // 是否启动
    protected AtomicBoolean started = new AtomicBoolean(false);
    // 线程名称
    protected String name;
    // 锁对象
    protected ReentrantLock lock;

    /**
     * 构造函数
     *
     * @param capacity 最大容量
     * @param name     名称
     */
    public RingBufferService(int capacity, String name) {
        this(capacity, name, null, null);
    }

    /**
     * 构造函数
     *
     * @param capacity 最大容量
     * @param name     名称
     * @param handler  处理器
     */
    public RingBufferService(int capacity, String name, EventHandler handler) {
        this(capacity, name, handler, null);
    }

    /**
     * 构造函数
     *
     * @param capacity 最大容量
     * @param name     名称
     * @param handler  处理器
     * @param lock     锁对象
     */
    public RingBufferService(int capacity, String name, EventHandler handler, ReentrantLock lock) {
        this.capacity = capacity;
        this.name = name;
        this.handler = handler;
        this.lock = lock == null ? new ReentrantLock() : lock;
        this.ringBuffer = new RingBuffer(capacity, this.lock);
    }

    @Override
    public void start() throws Exception {
        if (handler == null) {
            throw new IllegalStateException("handler can not be null");
        }
        lock.lock();
        try {
            if (started.compareAndSet(false, true)) {
                try {
                    dispatchThread =
                            new Thread(new DispatchTask(), name == null ? this.getClass().getSimpleName() : name);
                    dispatchThread.setDaemon(true);
                    dispatchThread.start();
                    ringBuffer.clear();
                    ringBuffer.start();
                } catch (Exception e) {
                    stop();
                    throw e;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void stop() {
        stop(5000);
    }

    /**
     * 停止，如果数据没用处理完，则等待一段时间直到完成或超时
     *
     * @param timeout 等待数据处理完的超时时间
     *                <li>>0 等待超时时间</li>
     *                <li>=0 等待处理完</li>
     *                <li><0 立即返回</li>
     */
    public void stop(long timeout) {
        lock.lock();
        try {
            if (started.compareAndSet(true, false)) {
                ringBuffer.stop();
                if (timeout < 0) {
                    return;
                }


            }
        } finally {
            lock.unlock();
        }
        // 等待数据处理完
        try {
            latch.await(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            // 当前线程终止
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public boolean isStarted() {
        return started.get();
    }

    public void setHandler(EventHandler handler) {
        this.handler = handler;
    }

    /**
     * 添加数据
     *
     * @param element 数据
     * @return 成功标示
     */
    public boolean add(final Object element) {
        if (!isStarted()) {
            return false;
        }
        return ringBuffer.add(element);
    }

    /**
     * 添加数据
     *
     * @param element 数据
     * @param timeout 超时时间
     *                <li>>0 等待超时时间</li>
     *                <li>=0 无限等待</li>
     *                <li><0 立即返回</li>
     * @return 成功标示
     */
    public boolean add(final Object element, final long timeout) {
        if (!isStarted()) {
            return false;
        }
        return ringBuffer.add(element, timeout);
    }

    /**
     * 是否还有未处理数据
     *
     * @return 是否还有未处理数据标示
     */
    public boolean hasRemaining() {
        return ringBuffer.hasRemaining();
    }

    public int remaining() {
        return ringBuffer.remaining();
    }

    public int getCapacity() {
        return ringBuffer.getCapacity();
    }

    /**
     * 清理缓冲区
     */
    public void clear() {
        ringBuffer.clear();
    }

    /**
     * 处理请求,时间敏感的监听器,为空也需要触发通知
     *
     * @param elements 请求列表
     * @timeAware 时间敏感
     */
    protected void dispatch(final Object[] elements, final boolean timeAware) {
        if (!timeAware && (elements == null || elements.length == 0)) {
            return;
        }
        try {
            handler.onEvent(elements);
        } catch (Throwable e) {
            handler.onException(e);
        }
    }

    /**
     * 缓冲区处理器
     */
    public interface EventHandler {

        /**
         * 派发数据
         *
         * @param elements 消息列表
         * @throws Exception
         */
        void onEvent(Object[] elements) throws Exception;

        /**
         * 出现异常处理
         *
         * @param exception 异常
         */
        void onException(Throwable exception);

        /**
         * 返回批量大小
         *
         * @return 批量大小
         */
        int getBatchSize();
    }

    /**
     * 必须满足批量大小才进行处理
     */
    public static interface BatchAwareHandler {

    }

    /**
     * 达到指定时间间隔也会触发处理
     */
    public static interface TimeAwareHandler {
        /**
         * 返回触发的时间间隔
         *
         * @return 时间间隔
         */
        int getInterval();
    }

    /**
     * 派发任务
     */
    protected class DispatchTask implements Runnable {
        @Override
        public void run() {
            Object[] elements;
            int timeout = 0;
            int batchSize = handler.getBatchSize();
            if (handler instanceof TimeAwareHandler) {
                timeout = ((TimeAwareHandler) handler).getInterval();
            }
            if (handler instanceof BatchAwareHandler) {
                batchSize = -handler.getBatchSize();
            }
            while (isStarted()) {
                elements = ringBuffer.get(batchSize, timeout);
                // 处理数据
                dispatch(elements, timeout > 0);
            }
            elements = ringBuffer.get(handler.getBatchSize(), -1);
            // 处理数据
            dispatch(elements, timeout > 0);
            latch.countDown();
        }
    }

}
