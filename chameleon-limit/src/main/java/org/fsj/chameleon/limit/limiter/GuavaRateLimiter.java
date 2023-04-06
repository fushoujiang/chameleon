package org.fsj.chameleon.limit.limiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

public class GuavaRateLimiter extends AbsCRateLimiter{

    private RateLimiter rateLimiter;

    public GuavaRateLimiter(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public boolean doTryAcquire(long timeout, TimeUnit unit) {
        return rateLimiter.tryAcquire(timeout,unit);
    }

    @Override
    public void doAcquire() {
        rateLimiter.acquire();
    }

}
