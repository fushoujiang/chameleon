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

    @Override
    public Lock createLock(FactoryParams<LockConfig> params) {
        final LockConfig lockConfig = params.getCreateParams();
        final String key = buildKey(lockConfig);
        return lockConfig.isFair() ? redisson.getFairLock(key) : redisson.getLock(key);
    }

    public String buildKey(LockConfig lockConfig) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(lockConfig.getProject());
        stringBuilder.append(lockConfig.getLockPrefix());
        stringBuilder.append(lockConfig.getParamsKey());
        return stringBuilder.toString();
    }

}
