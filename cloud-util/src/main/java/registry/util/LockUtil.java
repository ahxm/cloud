package registry.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Created by anzhen on 2016/5/23.
 */
public class LockUtil {

    /**
     * 锁失败
     */
    public static final int LOCK_FAIL = -2;

    /**
     * 锁
     *
     * @param lock    锁
     * @param timeout 超时时间
     *                <li>>0 等待超时时间</li>
     *                <li>=0 无限等待</li>
     *                <li><0 无限等待</li>
     * @return 剩余的超时时间
     * <li>>0 锁成功，timeout>0，剩余超时时间</li>
     * <li>0 锁成功，timeout=0</li>
     * <li>-1 锁成功，timeout<0</li>
     * <li>-2 失败</li>
     */
    public static long tryLock(final Lock lock, final long timeout) {
        long time;
        if (timeout > 0) {
            time = SystemClock.getInstance().now();
            try {
                if (lock.tryLock(timeout, TimeUnit.MILLISECONDS)) {
                    // 锁成功，获取剩余时间
                    time = timeout - (SystemClock.getInstance().now() - time);
                    if (time > 0) {
                        // 还有剩余时间
                        return time;
                    } else {
                        // 没用剩余时间，则释放锁
                        lock.unlock();
                    }
                }
                // 没用锁成功
                return LOCK_FAIL;
            } catch (InterruptedException e) {
                // 当前线程终止
                Thread.currentThread().interrupt();
                return LOCK_FAIL;
            }
        } else {
            lock.lock();
            return timeout == 0 ? 0 : -1;
        }
    }
}
