package org.fsj.chameleon.datasource.manager;

import com.alibaba.fastjson.JSON;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.daojia.gunpowder.feature.limit.entity.RateLimiterConfig;
import com.daojia.gunpowder.feature.limit.manager.convert.ApolloChangeConvert;
import com.google.common.base.Strings;


public class ApolloConfigManager extends AbsConfigManager implements ConfigChangeListener{

    private final Config config;

    private ApolloChangeConvert apolloChangeConvert;

    public ApolloConfigManager(Config config) {
        this.config = config;
        apolloChangeConvert = new ApolloChangeConvert();
    }

    @Override
    public RateLimiterConfig loadFromDateSource(RateLimiterConfig rateLimiterConfig) {
        final String key = buildKey(rateLimiterConfig);
        String configProperty = config.getProperty(key, "");
        if (Strings.isNullOrEmpty(configProperty)) return rateLimiterConfig;
        return JSON.parseObject(configProperty, RateLimiterConfig.class);
    }

    private String buildKey(RateLimiterConfig rateLimiterConfig){
        return rateLimiterConfig.getProject()+"_"+rateLimiterConfig.getGroup();
    }

    @Override
    public void onChange(ConfigChangeEvent configChangeEvent) {
        callBack(apolloChangeConvert.convert(configChangeEvent));
    }




}
