package com.ans.cloud.datasource;

import registry.plugin.PluginUtil;
import registry.util.URL;

/**
 * Created by anzhen on 2016/5/27.
 */
public class DataSourceFactory {

    private DataSourceConfig config;

    public DataSourceFactory(DataSourceConfig config) {
        this.config = config;
    }

    /**
     * 构建数据源
     *
     * @param config 数据源配置
     * @return 数据源
     */
    public static XDataSource build(DataSourceConfig config) {
        if (config == null) {
            return null;
        }
        DataSourceFactory factory = new DataSourceFactory(config);
        return factory.build();
    }

    /**
     * 构建数据源services
     *
     * @return 数据源
     */
    public XDataSource build() {
        DataSourceBuilder builder =
                PluginUtil.createService(DataSourceBuilder.class, URL.valueOf(config.getType() + "://"));
        if (builder == null) {
            return null;
        }
        return builder.build(config);
    }

}
