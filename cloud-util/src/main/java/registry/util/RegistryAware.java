package registry.util;

/**
 * Created by anzhen on 2016/5/25.
 */
public interface RegistryAware {
    /**
     * 设置注册中心
     *
     * @param registry
     */
    void setRegistry(Registry registry);
}
