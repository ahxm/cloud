package com.ans.cloud.task;

import com.ans.cloud.data.model.ConfigInfo;
import com.ans.cloud.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import registry.security.EncryptException;
import registry.security.Encrypts;
import registry.support.builder.ContextBuilder;
import registry.util.Context;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anzhen on 2016/5/31.
 */
public class DefaultContextBuilder implements ContextBuilder {

    private static final Logger logger = LoggerFactory.getLogger(DefaultContextBuilder.class);

    @Resource
    private ConfigService configService;

    protected String configType;

    // 外部初始化的参数
    protected Map<String,Object> parameters = new HashMap<String, Object>();


    @Override
    public Context createContext() {
        Context context = new Context(parameters);

        if(configService == null || configType == null || configType.isEmpty()){
            return context;
        }

        List<ConfigInfo> configs = configService.findByType(configType);
        if(configs !=null){
            for(ConfigInfo config: configs){
                if(config.isPassword()){
                    try {
                        String key = Encrypts.decryptByDes(config.getValue(),config.getKey());
                        context.put(config.getKey(),key);
                    } catch (EncryptException e) {
                        logger.error(String.format("failed to decrypt DES, cfg_group %s, cfg_key, %s cfg_value %s",
                                config.getGroup(), config.getKey(), config.getValue()), e);
                    }

                }else {
                    context.put(config.getKey(),config.getValue());
                }
            }
        }

        return context;
    }

    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
