package org.fsj.chameleon.limit.limiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

public class GuavaRateLimiter implements CRateLimiter{

    private RateLimiter rateLimiter;

    public GuavaRateLimiter(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
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
