package org.fsj.chameleon.limit.factory.params;

import org.fsj.chameleon.lang.factory.CacheFactoryParams;
import org.fsj.chameleon.limit.entity.RateLimiterConfig;

public class RateLimiterFactoryParams implements CacheFactoryParams<RateLimiterConfig> {

    private RateLimiterConfig rateLimitConfig;

    public RateLimiterFactoryParams(RateLimiterConfig rateLimitConfig) {
        this.rateLimitConfig = rateLimitConfig;
    }

    @Override
    public String getCacheKey() {
        return rateLimitConfig.getGroup()+"_"+rateLimitConfig.getKey();
    }

    @Override
    public RateLimiterConfig getCreateParams() {
        return rateLimitConfig;
    }
}
