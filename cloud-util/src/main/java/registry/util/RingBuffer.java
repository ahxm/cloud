package registry.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by anzhen on 2016/5/25.
 * 高性能环形缓冲区，适用于多个生产者和一个消费者
 */
public class RingBuffer implements LifeCycle {

    // 数据缓冲区
    protected final Object[] buffers;
    // 容积
    protected final int capacity;
    // 锁
    protected final ReentrantLock lock;
    // 没用空间写入数据条件
    protected final Condition fullCondition;
    // 有数据条件
    protected final Condition moreCondition;
    // 启动标示
    protected final AtomicBoolean started = new AtomicBoolean(false);
    // 上次写的最后位置
    protected int writerIndex;
    // 上次读的最后位置
    protected int readerIndex;

    public RingBuffer(int capacity) {
        this(capacity, null);
    }

    public RingBuffer(int capacity, ReentrantLock lock) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be greater than 0");
        }

        // 容量是2的指数
        int cap = 1;
        while (capacity > cap) {
            cap <<= 1;
        }

        this.capacity = cap;
        this.buffers = new Object[cap];
        this.writerIndex = this.readerIndex = -1;
        this.lock = lock == null ? new ReentrantLock() : lock;
        this.fullCondition = this.lock.newCondition();
        this.moreCondition = this.lock.newCondition();
    }

    @Override
    public void start() throws Exception {
        lock.lock();
        try {
            if (started.compareAndSet(false, true)) {

            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void stop() {
        lock.lock();
        try {
            if (started.compareAndSet(true, false)) {
                fullCondition.signalAll();
                moreCondition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 清理数据
     */
    public void clear() {
        lock.lock();
        try {
            readerIndex = writerIndex = -1;
            for (int i = 0; i < buffers.length; i++) {
                buffers[i] = null;
            }
            if (isStarted()) {
                fullCondition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isStarted() {
        return started.get();
    }

    /**
     * 添加数据
     *
     * @param element 数据
     * @return 成功标示
     */
    public boolean add(final Object element) {
        return add(element, 0);
    }

    /**
     * 添加数据
     *
     * @param element 数据
     * @param timeout 超时时间
     *                <li>>0 等待超时时间</li>
     *                <li>=0 无限等待</li>
     *                <li><0 没有空间立即返回</li>
     * @return 成功标示
     */
    public boolean add(final Object element, final long timeout) {
        if (element == null) {
            return false;
        }
        long time = LockUtil.tryLock(lock, timeout);
        if (time == LockUtil.LOCK_FAIL) {
            return false;
        }
        try {
            // 等待数据处理完
            if (!waitSpace(time)) {
                if (timeout < 0) {
                    return false;
                }
                return false;
            }
            buffers[index(++writerIndex)] = element;
            moreCondition.signalAll();
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取数据
     *
     * @param maxSize 最大条数
     *                <li>>0 获取的最大条数</li>
     *                <li>=0 不启用条数限制</li>
     *                <li>&lt;0 必须满足其绝对值数量</li>
     * @param timeout 超时时间
     *                <li>>0 没有指定数据则等待指定超时时间</li>
     *                <li>=0 没有指定数据则无限等待</li>
     *                <li>&lt;0 没有指定数据则立即返回</li>
     * @return 数据列表
     */
    public Object[] get(final int maxSize, final long timeout) {
        long time = LockUtil.tryLock(lock, timeout);
        if (time == LockUtil.LOCK_FAIL) {
            return new Object[0];
        }
        try {
            // 等待数据
            int count = waitData(-maxSize, timeout);
            if (count <= 0) {
                // 没有可读取数据
                return new Object[0];
            }
            // 修正索引
            if (readerIndex >= capacity - 1) {
                readerIndex -= capacity;
                writerIndex -= capacity;
            }
            Object[] result;
            if (maxSize > 0 && maxSize < count) {
                count = maxSize;
            }
            result = new Object[count];
            int writerPos = 0;
            int readerPos;
            while (writerPos < count) {
                readerPos = index(++readerIndex);
                result[writerPos++] = buffers[readerPos];
                // 设置为空，便于对象垃圾回收
                buffers[readerPos] = null;
            }
            fullCondition.signalAll();
            return result;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 等待写入空间
     *
     * @param timeout 超时时间(毫秒)
     *                <li>>0 等待超时时间</li>
     *                <li>=0 无限等待</li>
     *                <li>&lt;0 没有数据立即返回</li>
     * @return 成功标示
     */
    protected boolean waitSpace(final long timeout) {
        if (hasSpace()) {
            return true;
        }
        if (timeout < 0) {
            return false;
        }
        try {
            long time = timeout;
            long startTime;
            long endTime;
            while (isStarted() && !Thread.currentThread().isInterrupted()) {
                if (timeout == 0) {
                    fullCondition.await();
                } else {
                    startTime = SystemClock.getInstance().now();
                    if (!fullCondition.await(time, TimeUnit.MILLISECONDS)) {
                        return false;
                    }
                    endTime = SystemClock.getInstance().now();
                    time = time - (endTime - startTime);
                    // 超时了,则直接返回
                    if (time <= 0) {
                        return false;
                    }
                }
                // 多个生产者，空间可能被其它线程占用了，需要循环等待
                if (hasSpace()) {
                    return true;
                }
            }
            return false;
        } catch (InterruptedException e) {
            // 当前线程终止
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * 等待数据,为了尽可能处理完数据,不校验是否关闭
     *
     * @param count   条数
     *                <li>>0 必须满足指定条数</li>
     *                <li>&lt;=0 不需要满足指定条数</li>
     * @param timeout 超时时间
     *                <li>>0 没有数据等待超时时间</li>
     *                <li>=0 没有数据无限等待</li>
     *                <li>&lt;0 没有数据立即返回</li>
     * @return 可读取数据条数
     */
    protected int waitData(final int count, final long timeout) {
        int size = writerIndex - readerIndex;
        if (timeout < 0) {
            return size;
        }
        // 有可读取数据，没有条数限制
        if (size > 0 && count <= 0) {
            return size;
        }

        long time = timeout;
        long startTime;
        long endTime;
        try {
            while (isStarted() && !Thread.currentThread().isInterrupted()) {
                if (timeout == 0) {
                    // 无限等待
                    moreCondition.await();
                    size = writerIndex - readerIndex;
                    if (size > 0 && (count <= 0 || size >= count)) {
                        return size;
                    }
                } else {
                    // 等待超时时间
                    startTime = SystemClock.getInstance().now();
                    moreCondition.await(time, TimeUnit.MILLISECONDS);
                    endTime = SystemClock.getInstance().now();
                    // 计算剩余超时时间
                    time = time - (endTime - startTime);
                    // 计算数据条数
                    size = writerIndex - readerIndex;
                    // 如果没有时间或有足够数据了
                    if (time <= 0 || (size > 0 && (count <= 0 || size >= count))) {
                        return size;
                    }
                }
            }
            return writerIndex - readerIndex;
        } catch (InterruptedException e) {
            // 当前线程终止
            Thread.currentThread().interrupt();
            return writerIndex - readerIndex;
        }
    }

    /**
     * 是否有空间
     *
     * @return 有空间标示
     */
    protected boolean hasSpace() {
        return isStarted() && (writerIndex - readerIndex) < capacity;
    }

    /**
     * 计算数组中的索引
     *
     * @param position 全局位置
     * @return 位置
     */
    protected int index(final int position) {
        return position & (capacity - 1);
    }

    /**
     * 是否有数据未处理
     *
     * @return 有数据未处理标示
     */
    protected boolean hasRemaining() {
        return writerIndex != readerIndex;
    }

    public int getCapacity() {
        return capacity;
    }

    public int remaining() {
        return writerIndex - readerIndex;
    }
}
