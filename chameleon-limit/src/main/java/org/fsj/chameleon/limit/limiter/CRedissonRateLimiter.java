package org.fsj.chameleon.limit.limiter;

import org.redisson.api.RRateLimiter;

import java.util.concurrent.TimeUnit;

public class CRedissonRateLimiter extends AbsCRateLimiter{

    private RRateLimiter rateLimiter ;

    public CRedissonRateLimiter(RRateLimiter redissonRateLimiter) {
        this.rateLimiter = redissonRateLimiter;
    }

    @Override
    public boolean doTryAcquire(long timeout, TimeUnit unit) {
        return rateLimiter.tryAcquire(timeout,unit);    }

    @Override
    public void doAcquire() {
        rateLimiter.acquire();

    }
}
