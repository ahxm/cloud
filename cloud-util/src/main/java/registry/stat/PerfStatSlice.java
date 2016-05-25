package registry.stat;

/**
 * Created by anzhen on 2016/5/25.
 */
public interface PerfStatSlice {


    /**
     * 开始时间
     *
     * @return 开始时间
     */
    long getStartTime();

    /**
     * 设置开始时间
     *
     * @param startTime 开始时间
     */
    void setStartTime(long startTime);

    /**
     * 获取结束时间
     *
     * @return 结束时间
     */
    long getEndTime();

    /**
     * 设置结束时间
     *
     * @param endTime 结束时间
     */
    void setEndTime(long endTime);

    /**
     * 初始化
     */
    void clear();
}
