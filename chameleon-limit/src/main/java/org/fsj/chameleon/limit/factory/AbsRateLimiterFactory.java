package org.fsj.chameleon.limit.factory;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.fsj.chameleon.datasource.manager.AbsConfigManager;
import org.fsj.chameleon.lang.ConfigManager;
import org.fsj.chameleon.lang.factory.AbsCacheFactory;
import org.fsj.chameleon.lang.factory.FactoryParams;
import org.fsj.chameleon.limit.entity.RateLimiterConfig;
import org.fsj.chameleon.limit.limiter.CRateLimiter;
import org.fsj.chameleon.limit.manager.NacosConfigManger;


public abstract class AbsRateLimiterFactory extends AbsCacheFactory<CRateLimiter, RateLimiterConfig> {


    public AbsRateLimiterFactory(ConfigManager<FactoryParams<RateLimiterConfig>> manager) {
        this.manager = manager;
    }

    public AbsRateLimiterFactory() {
    }

    private ConfigManager<FactoryParams<RateLimiterConfig>> manager;

    public abstract CRateLimiter createRateLimiter(FactoryParams<RateLimiterConfig> params);

    @Override
    public CRateLimiter create(FactoryParams<RateLimiterConfig> params) {
        checkArguments(params.getCreateParams());
        if (manager != null) {
            final FactoryParams<RateLimiterConfig> config = manager.getConfig(params);
            checkArguments(config.getCreateParams());
            return createRateLimiter(config);
        }
        return createRateLimiter(params);
    }


    private void checkArguments(RateLimiterConfig rateLimiterConfig) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(rateLimiterConfig.getGroup()), "限流器分组group不能为空");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(rateLimiterConfig.getKey()), "限流器项目key不能为空");
    }


}
