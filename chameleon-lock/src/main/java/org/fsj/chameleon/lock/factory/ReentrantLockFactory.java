package org.fsj.chameleon.lock.factory;

import org.fsj.chameleon.lang.factory.FactoryParams;
import org.fsj.chameleon.lock.entity.LockConfig;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockFactory extends AbsLockFactory{
    @Override
    public Lock createLock(FactoryParams<LockConfig> params) {
        final LockConfig createParams = params.getCreateParams();
        return new ReentrantLock(createParams.isFair());
    }

}
