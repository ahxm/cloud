package com.ans.cloud.datasource;


import registry.plugin.ServicePlugin;

/**
 * 数据源构造器
 */
public interface DataSourceBuilder extends ServicePlugin {

    /**
     * 创建连接池
     *
     * @param config 连接池配置
     * @return 连接池
     */
    XDataSource build(DataSourceConfig config);

}
