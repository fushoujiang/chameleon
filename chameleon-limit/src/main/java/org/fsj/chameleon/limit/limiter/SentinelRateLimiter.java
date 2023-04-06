package org.fsj.chameleon.limit.limiter;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import java.util.concurrent.TimeUnit;

public class SentinelRateLimiter extends AbsCRateLimiter {

    private String sourceName;

    public SentinelRateLimiter(String sourceName) {
        this.sourceName = sourceName;
    }


    @Override
    public boolean doTryAcquire(long timeout, TimeUnit unit) {
        doAcquire();
        return true;
    }

    public void doAcquire() {
        Entry entry = null;
        try {
            entry = SphU.entry(sourceName);
        } catch (BlockException e) {
            e.printStackTrace();
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }

}
