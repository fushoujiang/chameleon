package org.fsj.chameleon.lock.factory;

import org.fsj.chameleon.lang.ConfigManager;
import org.fsj.chameleon.lang.factory.AbsCacheFactory;
import org.fsj.chameleon.lang.factory.FactoryParams;
import org.fsj.chameleon.lock.entity.LockConfig;

import java.util.concurrent.locks.Lock;


public abstract class AbsLockFactory extends AbsCacheFactory<Lock, LockConfig> {


    public AbsLockFactory(ConfigManager<FactoryParams<LockConfig>> manager) {
        this.manager = manager;
    }

    public AbsLockFactory() {

    }

    private ConfigManager<FactoryParams<LockConfig>> manager;

    public abstract Lock createLock(FactoryParams<LockConfig> params);

    @Override
    public Lock create(FactoryParams<LockConfig> params) {
        if (manager != null) {
            final FactoryParams<LockConfig> config = manager.getConfig(params);
            return createLock(config);
        }
        return createLock(params);
    }





}
