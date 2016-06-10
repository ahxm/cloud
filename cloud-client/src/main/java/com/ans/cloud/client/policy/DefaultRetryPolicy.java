package com.ans.cloud.client.policy;


/**
 * Created by anzhen on 2016/6/9.
 */
public class DefaultRetryPolicy implements RetryPolicy {

    private int maxRetryTimes = 1;

    private int retryInterval  =100;

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
    }

    /**
     * 根据重试次数获取下次重试的时间间隔.
     *
     * @param retryTimes 当前重试次数
     * @return 下次重试的时间间隔, 如果不再重试返回-1
     */
    @Override
    public int getRetryInterval(int retryTimes) {
        if(maxRetryTimes < retryTimes){
            return -1;
        }

        return retryInterval;
    }
}
