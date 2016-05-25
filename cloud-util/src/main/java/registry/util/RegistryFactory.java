package registry.util;

import registry.plugin.ServicePlugin;

/**注册中心工厂类
 * Created by anzhen on 2016/5/25.
 */
public interface RegistryFactory extends ServicePlugin {

    /**
     * 获取创建好的注册中心
     *
     * @return
     */
    Registry getRegistry() throws RegistryException;

}
