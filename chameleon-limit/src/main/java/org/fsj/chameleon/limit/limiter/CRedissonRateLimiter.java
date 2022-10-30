package org.fsj.chameleon.limit.limiter;

import org.redisson.api.RRateLimiter;

import java.util.concurrent.TimeUnit;

public class CRedissonRateLimiter implements CRateLimiter{

    private RRateLimiter rateLimiter ;

    public CRedissonRateLimiter(RRateLimiter redissonRateLimiter) {
        this.rateLimiter = redissonRateLimiter;
    }

    @Override
    public boolean tryAcquire(long timeout, TimeUnit unit) {
        return rateLimiter.tryAcquire(timeout,unit);
    }

    @Override
    public void acquire() {
        rateLimiter.acquire();
    }
}
