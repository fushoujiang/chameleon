package org.fsj.chameleon.lock.factory;

import org.fsj.chameleon.lang.factory.FactoryParams;
import org.fsj.chameleon.lock.entity.LockConfig;
import org.redisson.Redisson;

import java.util.concurrent.locks.Lock;

public class RedissonLockFactory extends AbsLockFactory {
    Redisson redisson;

    public RedissonLockFactory(Redisson redisson) {
        this.redisson = redisson;
    }

    public RedissonLockFactory() {
        this.redisson = (Redisson) Redisson.create();
    }


    @Override
    public Lock createLock(FactoryParams<LockConfig> params) {
        final LockConfig lockConfig = params.getCreateParams();
        return lockConfig.isFair() ? redisson.getFairLock(lockConfig.getLockKey()) : redisson.getLock(lockConfig.getLockKey());
    }


}
