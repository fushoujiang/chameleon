package org.fsj.chameleon.limit.limiter;


import java.util.concurrent.TimeUnit;

public abstract class AbsCRateLimiter implements CRateLimiter{

    public  abstract boolean doTryAcquire(long timeout, TimeUnit unit);

    public  abstract void doAcquire();


    @Override
    public boolean tryAcquire(long timeout, TimeUnit unit)  {
        //before
        final boolean acquire = doTryAcquire(timeout, unit);
        //after
        return acquire;
    }

    @Override
    public void acquire()  {
        doAcquire();
    }
}
