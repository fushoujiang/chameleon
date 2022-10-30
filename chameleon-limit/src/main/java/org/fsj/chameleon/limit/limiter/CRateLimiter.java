package org.fsj.chameleon.limit.limiter;

import java.util.concurrent.TimeUnit;

public interface CRateLimiter {

    boolean tryAcquire(long timeout, TimeUnit unit);

    void acquire();

}
