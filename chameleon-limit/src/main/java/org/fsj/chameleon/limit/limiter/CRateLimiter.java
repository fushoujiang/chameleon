package org.fsj.chameleon.limit.limiter;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;

import java.util.concurrent.TimeUnit;

public interface CRateLimiter {

    boolean tryAcquire(long timeout, TimeUnit unit) ;

    void acquire() throws BlockException;

}
