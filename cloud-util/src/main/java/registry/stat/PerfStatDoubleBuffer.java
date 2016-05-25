package registry.stat;

import registry.util.SystemClock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by anzhen on 2016/5/25.
 */
public class PerfStatDoubleBuffer <T extends PerfStatSlice>{
    // 读缓冲区
    protected T readStat;
    // 写缓冲区
    protected T writeStat;
    // 读写锁
    protected ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public PerfStatDoubleBuffer(T readStat, T writeStat) {
        this.readStat = readStat;
        this.writeStat = writeStat;
    }

    public T getReadStat() {
        return readStat;
    }

    /**
     * 切片
     *
     * @return 当前可读取的数据
     */
    public T slice() {
        lock.writeLock().lock();
        try {
            T stat = writeStat;
            writeStat = readStat;
            readStat = stat;

            long now = SystemClock.getInstance().now();
            writeStat.setStartTime(now);
            writeStat.clear();
            readStat.setEndTime(now);
            return readStat;
        } finally {
            lock.writeLock().unlock();
        }

    }
}
