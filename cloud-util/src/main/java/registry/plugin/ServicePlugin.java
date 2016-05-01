package registry.plugin;

import registry.util.URL;

/**
 * Created by anzhen on 2016/5/1.
 */
public interface ServicePlugin {

    /**
     * 返回类型
     *
     * @return
     */
    String getType();

    /**
     * 设置URL
     *
     * @param url
     */
    void setUrl(URL url);
}
