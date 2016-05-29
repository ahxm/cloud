package com.ans.cloud.datasource;

import registry.security.EncryptException;
import registry.security.Encrypts;
import registry.util.EncryptUtil;
import registry.util.URL;

/**
 * Created by anzhen on 2016/5/25.
 */
public class HikariDataSourceBuilder implements DataSourceBuilder {

    public static final String HIKARI_HOUSEKEEPING_PERIOD = "com.zaxxer.hikari.housekeeping.periodMs";

    @Override
    public XDataSource build(DataSourceConfig config) {
        String password = null;
        try {
            password = Encrypts.decryptByDes(config.getPassword(), config.getUser());
        } catch (EncryptException e) {
            throw new IllegalStateException("decrypt password error.");
        }

        HikariXDataSource ds = new HikariXDataSource();
        ds.setJdbcUrl(config.getUrl());
        ds.setDriverClassName(config.getDriver());
        ds.setUsername(config.getUser());
        ds.setPassword(password);
        ds.setConnectionTimeout(config.getConnectionTimeout());
        ds.setIdleTimeout(config.getIdleTimeout());
        ds.setMaxLifetime(config.getMaxLifetime());
        ds.setMaximumPoolSize(config.getMaxPoolSize());
        ds.setMinimumIdle(config.getMinIdle());
        ds.setConnectionTestQuery(config.getValidationQuery());
        ds.setAutoCommit(config.isAutoCommit());
        ds.setTransactionIsolation(config.getTransactionIsolation());
        ds.setReadOnly(config.isReadOnly());
        ds.setCatalog(config.getCatalog());
        System.setProperty(HIKARI_HOUSEKEEPING_PERIOD, String.valueOf(config.getCleanupInterval()));

        if (config.getConnectionProperties() != null) {
            String[] properties = config.getConnectionProperties().split(";");
            int pos;
            String name, value;
            for (String property : properties) {
                pos = property.indexOf('=');
                if (pos > 0 && pos < property.length() - 1) {
                    name = property.substring(0, pos);
                    value = property.substring(pos + 1);
                    ds.addDataSourceProperty(name, value);
                }
            }
        }
        return ds;
    }

    @Override
    public String getType() {
        return "HikariCP";
    }

    @Override
    public void setUrl(URL url) {

    }
}
