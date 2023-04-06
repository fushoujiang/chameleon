package org.fsj.chameleon.limit.factory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.fsj.chameleon.datasource.manager.LocalConfigManager;
import org.fsj.chameleon.lang.ConfigManager;
import org.fsj.chameleon.lang.factory.AbsCacheFactory;
import org.fsj.chameleon.limit.entity.RateLimiterConfig;
import org.fsj.chameleon.limit.limiter.CRateLimiter;



public abstract class AbsRateLimiterFactory extends AbsCacheFactory<RateLimiterConfig,CRateLimiter> {

    private ConfigManager<RateLimiterConfig> manager;


    public AbsRateLimiterFactory(ConfigManager<RateLimiterConfig> manager) {
        this.manager = manager;
    }

    public AbsRateLimiterFactory() {
        manager = new LocalConfigManager<>();
    }


    public abstract CRateLimiter createRateLimiter(RateLimiterConfig config);


    @Override
    public CRateLimiter create(RateLimiterConfig rateLimiterConfig) {
        checkArguments(rateLimiterConfig);
        final RateLimiterConfig config = manager.getConfig(rateLimiterConfig);
        checkArguments(config);
        return createRateLimiter(config);

    }

    @Override
    public String getCacheKey(RateLimiterConfig rateLimiterConfig) {
        return rateLimiterConfig.getKey();
    }


    private void checkArguments(RateLimiterConfig rateLimiterConfig) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(rateLimiterConfig.getGroup()), "限流器分组group不能为空");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(rateLimiterConfig.getKey()), "限流器项目key不能为空");
    }


}
