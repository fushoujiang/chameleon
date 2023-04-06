package org.fsj.chameleon.limit.manager;

import com.alibaba.nacos.api.config.ConfigService;
import org.fsj.chameleon.datasource.manager.AbsNacosConfigManager;
import org.fsj.chameleon.limit.convert.JsonConvert;
import org.fsj.chameleon.limit.entity.RateLimiterConfig;

import java.util.concurrent.Executor;

public class NacosConfigManger extends AbsNacosConfigManager<RateLimiterConfig> {

    public NacosConfigManger(ConfigService configService) {
        super( new JsonConvert(), configService);
    }

    @Override
    public String buildCacheKey(RateLimiterConfig rateLimiterConfig) {
        return rateLimiterConfig.getGroup()+"_"+rateLimiterConfig.getKey();
    }



    @Override
    public String getDateId(RateLimiterConfig rateLimiterConfig) {
        return rateLimiterConfig.getKey();
    }

    @Override
    public String getGroup(RateLimiterConfig rateLimiterConfig) {
        return rateLimiterConfig.getGroup();
    }

    @Override
    public Executor getExecutor() {
        return null;
    }
}
