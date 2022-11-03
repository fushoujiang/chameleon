package org.fsj.chameleon.lock.factory;

import org.fsj.chameleon.lang.factory.FactoryParams;
import org.fsj.chameleon.lock.entity.LockConfig;

public class LockConfigFactoryParams implements FactoryParams<LockConfig> {

    private LockConfig lockConfig;

    public LockConfigFactoryParams(LockConfig lockConfig) {
        this.lockConfig = lockConfig;
    }

    @Override
    public LockConfig getCreateParams() {
        return lockConfig;
    }

    public LockConfig getLockConfig() {
        return lockConfig;
    }

    public LockConfigFactoryParams setLockConfig(LockConfig lockConfig) {
        this.lockConfig = lockConfig;
        return this;
    }
}
