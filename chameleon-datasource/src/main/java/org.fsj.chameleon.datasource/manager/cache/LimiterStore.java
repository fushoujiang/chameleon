package org.fsj.chameleon.datasource.manager.cache;

import com.daojia.gunpowder.feature.limit.entity.RateLimiterConfig;
import com.daojia.gunpowder.feature.limit.limiter.DRateLimiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LimiterStore {


    public static LimiterStore getInstance() {
        return LimiterHolderHolder.INSTANCE;
    }

    private static class LimiterHolderHolder {
        private static final LimiterStore INSTANCE = new LimiterStore();
    }

    private static final ConcurrentMap<String, DRateLimiter> RATE_LIMITER_CACHE = new ConcurrentHashMap<>();

    public DRateLimiter getCache(RateLimiterConfig  rateLimiterConfig) {
        final String key = buildCacheKey(rateLimiterConfig);
        return RATE_LIMITER_CACHE.get(key);
    }

    public void putCache(RateLimiterConfig rateLimiterConfig,final DRateLimiter limiter) {
        final String cacheKey = buildCacheKey(rateLimiterConfig);
        RATE_LIMITER_CACHE.put(cacheKey, limiter);
    }


    public String buildCacheKey(RateLimiterConfig rateLimiterConfig) {
        return rateLimiterConfig.getProject() + "." + rateLimiterConfig.getGroup();
    }

    public void update(RateLimiterConfig rateLimiterConfig,DRateLimiter rateLimiter) {
        putCache(rateLimiterConfig,rateLimiter);
    }





}
