package org.fsj.chameleon.lock.factory;

import org.fsj.chameleon.lang.factory.AbsCacheFactory;
import org.fsj.chameleon.lock.entity.LockConfig;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;


public abstract class AbsLockFactory extends AbsCacheFactory<LockConfig,Lock> {


    @Override
    public String getCacheKey(LockConfig lockConfig) {
        return lockConfig.getLockPrefix()+"_"+ Arrays.toString(lockConfig.getKeys());
    }
}
