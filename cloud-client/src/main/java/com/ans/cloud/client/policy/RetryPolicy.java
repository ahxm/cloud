package com.ans.cloud.client.policy;

/**
 * Created by anzhen on 2016/6/9.
 */
public interface RetryPolicy {


    /**
     * 根据重试次数获取下次重试的时间间隔.
     *
     * @param retryTimes 当前重试次数
     * @return 下次重试的时间间隔, 如果不再重试返回-1
     */
    public int getRetryInterval(int retryTimes);

}
