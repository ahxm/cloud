package registry.util;

/**
 * Created by anzhen on 2016/4/30.
 * 生命周期管理
 */
public interface LifeCycle {

    void start() throws Exception;

    void stop();

    boolean isStarted();

}
