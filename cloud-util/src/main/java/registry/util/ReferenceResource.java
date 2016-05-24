package registry.util;

/**
 * Created by anzhen on 2016/5/24.
 */
public interface ReferenceResource {

    /**
     * 保留资源
     */
    void retain();

    /**
     * 释放资源
     */
    void release();

    /**
     * 是否使用中
     * @return 使用标示
     */
    boolean isUsed();
}
