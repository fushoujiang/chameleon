package org.fsj.chameleon.lock.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.fsj.chameleon.lock.DistributedLock;
import org.fsj.chameleon.lock.entity.LockConfig;
import org.fsj.chameleon.lock.factory.AbsLockFactory;
import org.fsj.chameleon.lock.util.LockUtils;

import java.lang.annotation.Annotation;

@Aspect
public class DistributedLockInterceptor extends AbsLockInterceptor{


    public DistributedLockInterceptor(AbsLockFactory absLockFactory) {
        super(absLockFactory);
    }

    public DistributedLockInterceptor() {
    }

    @Around("@annotation(distributedLock)")
    public Object around(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        return lockAround(joinPoint,distributedLock);
    }
    @Override
    public LockConfig params2LockConfig(ProceedingJoinPoint joinPoint, Annotation annotation) {
        DistributedLock distributedLock = (DistributedLock) annotation;
        final LockConfig lockConfig = new LockConfig()
                .setLockPrefix(distributedLock.lockPrefix())
                .setKeyIndexes(distributedLock.keyIndexes())
                .setKeys(distributedLock.keys())
                .setLockFailMethod(distributedLock.lockFailMethod())
                .setTimeout(distributedLock.timeout());
        lockConfig.setLockKey(LockUtils.getLockKey(joinPoint.getArgs(),lockConfig));
        return lockConfig;
    }
}
