package org.fsj.chameleon.limit.factory;

import org.fsj.chameleon.lang.ConfigManager;
import org.fsj.chameleon.lang.factory.FactoryParams;
import org.fsj.chameleon.limit.entity.RateLimiterConfig;
import org.fsj.chameleon.limit.limiter.CRateLimiter;
import org.fsj.chameleon.limit.limiter.CRedissonRateLimiter;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;


public class RedissonRateLimiterFactory extends AbsRateLimiterFactory {

    private RedissonClient redisson;

    public RedissonRateLimiterFactory(RedissonClient redisson) {
        this.redisson = redisson;
    }


    public RedissonRateLimiterFactory(ConfigManager<FactoryParams<RateLimiterConfig>> manager, RedissonClient redisson) {
        super(manager);
        this.redisson = redisson;
    }

    @Override
    public CRateLimiter createRateLimiter(FactoryParams<RateLimiterConfig> params) {
        final RateLimiterConfig rateLimiterConfig = params.getCreateParams();
        RRateLimiter rateLimiter = redisson.getRateLimiter(rateLimiterConfig.getGroup() + ":" + rateLimiterConfig.getKey());
        rateLimiter.setRate(rateLimiterConfig.isCluster() ? RateType.OVERALL : RateType.PER_CLIENT, rateLimiterConfig.getPerSecond(), 1, RateIntervalUnit.SECONDS);
        return new CRedissonRateLimiter(rateLimiter);
    }
}
