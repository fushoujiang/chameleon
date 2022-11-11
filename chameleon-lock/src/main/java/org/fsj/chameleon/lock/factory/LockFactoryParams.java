package org.fsj.chameleon.lock.factory;

import org.fsj.chameleon.lang.factory.CacheFactoryParams;
import org.fsj.chameleon.lock.entity.LockConfig;

import java.util.Arrays;

public class LockFactoryParams  implements CacheFactoryParams<LockConfig> {
    private LockConfig lockConfig;


    public LockFactoryParams(LockConfig lockConfig) {
        this.lockConfig = lockConfig;
    }

    public LockConfig getLockConfig() {
        return lockConfig;
    }

    public LockFactoryParams setLockConfig(LockConfig lockConfig) {
        this.lockConfig = lockConfig;
        return this;
    }

    @Override
    public String getCacheKey() {
        return lockConfig.getLockPrefix()+"_"+ Arrays.toString(lockConfig.getKeys());
    }

    @Override
    public LockConfig getCreateParams() {
        return lockConfig;
    }
}
