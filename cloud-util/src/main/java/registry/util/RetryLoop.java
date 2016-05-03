package registry.util;

import java.util.concurrent.Callable;

/**
 * Created by anzhen on 2016/5/3.
 */
public class RetryLoop {

    /**
     * 循环重试
     *
     * @param retryPolicy 重试策略
     * @param proc        回调函数
     * @param <T>
     * @return 返回结果
     * @throws Exception
     */

    public static <T> T execute(RetryPolicy retryPolicy, RetryCallable<T> proc) throws Exception {

        if (proc == null) {
            throw new IllegalArgumentException("proc can not be null");

        }

        if (retryPolicy == null) {
            throw new IllegalArgumentException("retryPolicy can not be null");
        }

        int retryCount = 0;
        long startTime = SystemClock.getInstance().now();
        while (proc.shouldContinue()) {
            try {
                return proc.call();

            } catch (Exception e) {
                if (proc.onException(e)) {
                    long now = SystemClock.getInstance().now();
                    long time = retryPolicy.getIime(now, ++retryCount, startTime);
                    if (time <= 0) {
                        throw e;
                    }
                    Thread.sleep(time - now);
                } else {
                    throw e;
                }
            }
        }
        return null;
    }

    public static interface RetryCallable<T> extends Callable<T> {

        /**
         * 出现异常，是否要继续
         *
         * @param e 异常
         * @return <li>true 继续重试</li>
         * <li>false 退出重试</li>
         */
        boolean onException(Exception e);

        /**
         * 是否要继续
         *
         * @return 是否要继续重试
         */
        boolean shouldContinue();
    }


}
