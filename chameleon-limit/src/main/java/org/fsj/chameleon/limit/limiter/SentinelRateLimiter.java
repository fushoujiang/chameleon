package org.fsj.chameleon.limit.limiter;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import java.util.concurrent.TimeUnit;

public class SentinelRateLimiter implements CRateLimiter {

    private String sourceName;

    public SentinelRateLimiter(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public boolean tryAcquire(long timeout, TimeUnit unit) throws BlockException {
        doAcquire();
        return true;
    }


    @Override
    public void acquire() throws BlockException {
        doAcquire();
    }

    private void doAcquire() throws BlockException {
         Entry entry = null;
        try  {
            entry = SphU.entry(sourceName);
        }finally {
            assert entry != null;
            entry.exit();
        }
    }

}
