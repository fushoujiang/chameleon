package org.fsj.chameleon.datasource.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.daojia.gunpowder.feature.limit.entity.RateLimiterConfig;
import com.daojia.gunpowder.feature.limit.manager.convert.NacosChangeConvert;

import java.util.Properties;
import java.util.concurrent.Executor;

public class NacosConfigManager extends AbsConfigManager {


    private ConfigService configService;

    private NacosChangeConvert nacosChangeConvert;

    public NacosConfigManager(ConfigService configService) {
        this.configService = configService;
        nacosChangeConvert = new NacosChangeConvert();
    }

    private static int TIME_OUT = 3;


    public NacosConfigManager(String serviceAddress) {
        init(serviceAddress);
        nacosChangeConvert = new NacosChangeConvert();
    }

    @Override
    public RateLimiterConfig loadFromDateSource(RateLimiterConfig rateLimiterConfig) {
        try {
            final String dateId = rateLimiterConfig.getGroup();
            final String group = rateLimiterConfig.getProject();
            String config = configService.getConfigAndSignListener(dateId, group, TIME_OUT, new NacosConfigChangeListener(this, dateId, group));
            return JSON.parseObject(config, RateLimiterConfig.class);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return rateLimiterConfig;
    }

    private void init(String serviceAddress) {
        Properties properties = new Properties();
        properties.put("serverAddr", serviceAddress);
        try {
            configService = NacosFactory.createConfigService(properties);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }


    static class NacosConfigChangeListener implements Listener {
        private NacosConfigManager nacosConfigManager;

        private String dataId;
        private String group;

        public NacosConfigChangeListener(NacosConfigManager nacosConfigManager, String dataId, String group) {
            this.nacosConfigManager = nacosConfigManager;
            this.dataId = dataId;
            this.group = group;
        }

        @Override
        public Executor getExecutor() {
            return null;
        }

        @Override
        public void receiveConfigInfo(String configInfo) {
            RateLimiterConfig rateLimiterConfig = JSON.parseObject(configInfo, RateLimiterConfig.class);
            rateLimiterConfig.setProject(group);
            rateLimiterConfig.setGroup(dataId);
            nacosConfigManager.callBack(nacosConfigManager.nacosChangeConvert.convert(configInfo));
        }
    }
}
