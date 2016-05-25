package registry.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by anzhen on 2016/5/25.
 */
public class Threads {

    /**
     * 调用任务
     *
     * @param tasks      任务
     * @param maxThreads 最大线程数
     * @param name       线程名称
     * @param callback   回调
     * @return 任务结果
     * @throws InterruptedException
     */
    public static <T, M extends Callable<T>> List<T> invoke(List<M> tasks, int maxThreads, String name,
                                                            Callback callback) throws InterruptedException {
        if (tasks == null || tasks.isEmpty()) {
            return new ArrayList<T>();
        }
        if (maxThreads < 0) {
            throw new IllegalArgumentException("maxThreads must be greater than 0");
        }
        // 请求数量
        int threads = Math.min(maxThreads, tasks.size());
        // 构造线程池
        ExecutorService executorService;
        if (name != null && !name.isEmpty()) {
            executorService = Executors.newFixedThreadPool(threads, new NamedThreadFactory(name));
        } else {
            executorService = Executors.newFixedThreadPool(threads);
        }
        try {
            // 并发调用
            return invoke(tasks, executorService, callback);
        } finally {
            executorService.shutdownNow();
        }
    }

    /**
     * 调用任务
     *
     * @param tasks           任务
     * @param executorService 线程池
     * @param callback        回调
     * @return 任务结果
     * @throws InterruptedException
     */
    public static <T, M extends Callable<T>> List<T> invoke(List<M> tasks, ExecutorService executorService,
                                                            Callback callback) throws InterruptedException {
        List<T> results = new ArrayList<T>();
        if (tasks == null || tasks.isEmpty()) {
            return results;
        }
        if (executorService == null) {
            throw new IllegalArgumentException("executorService can not be null");
        }

        // 并发调用
        List<Future<T>> futures = executorService.invokeAll(tasks);

        Throwable exception;
        for (Future<T> future : futures) {
            try {
                T ret = future.get();
                if (ret != null) {
                    results.add(ret);
                }
            } catch (InterruptedException e) {
                // 终止了
                throw e;
            } catch (ExecutionException e) {
                // 执行出错
                exception = e.getCause();
                if (callback != null) {
                    if (exception != null) {
                        callback.onException(exception);
                    } else {
                        callback.onException(e);
                    }
                }
            }
        }
        return results;
    }

    /**
     * 回调
     */
    public static interface Callback {

        /**
         * 出现异常
         *
         * @param e 异常
         */
        void onException(Throwable e);

    }

}
