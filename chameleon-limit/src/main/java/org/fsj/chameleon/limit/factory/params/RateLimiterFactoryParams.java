package org.fsj.chameleon.limit.factory.params;

import org.fsj.chameleon.lang.factory.CacheFactoryParams;
import org.fsj.chameleon.limit.entity.RateLimiterConfig;

public class RateLimiterFactoryParams implements CacheFactoryParams<RateLimiterConfig> {

    private RateLimiterConfig rateLimitConfig;


    public RateLimiterConfig getRateLimitConfig() {
        return rateLimitConfig;
    }

    public RateLimiterFactoryParams setRateLimitConfig(RateLimiterConfig rateLimitConfig) {
        this.rateLimitConfig = rateLimitConfig;
        return this;
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
