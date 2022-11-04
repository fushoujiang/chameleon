package org.fsj.chameleon.lock.interceptor;


import com.google.common.base.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.fsj.chameleon.lang.util.FallBackUtil;
import org.fsj.chameleon.lock.LockFailException;
import org.fsj.chameleon.lock.entity.LockConfig;
import org.fsj.chameleon.lock.factory.AbsLockFactory;
import org.fsj.chameleon.lock.factory.LockConfigFactoryParams;
import org.fsj.chameleon.lock.factory.RedissonLockFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public abstract class AbsLockInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbsLockInterceptor.class);

    private AbsLockFactory absLockFactory;

    public AbsLockInterceptor(AbsLockFactory absLockFactory) {
        this.absLockFactory = absLockFactory;
    }
    public AbsLockInterceptor() {
        this.absLockFactory = new RedissonLockFactory();
    }

    public Object lockAround(ProceedingJoinPoint joinPoint, Annotation annotation) throws Throwable{
        final LockConfigFactoryParams lockConfigEntity = params2LockConfig(joinPoint,annotation);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final LockConfig lockConfig = lockConfigEntity.getLockConfig();
        final String lockFailMethod = lockConfig.getLockFailMethod();
        final String lockKey = lockConfig.getLockKey();
        Lock lock = absLockFactory.get(lockConfigEntity);
        if(lock.tryLock(lockConfig.getTimeout(),TimeUnit.MILLISECONDS)){
            LOGGER.info("{}-{}, get lock success:{}", joinPoint.getTarget().getClass().getName(), methodSignature.getName(), lockKey);
            try {
                return joinPoint.proceed();
            }finally {
                lock.unlock();
                LOGGER.info("{}-{}, release lock:{}", joinPoint.getTarget().getClass().getName(), methodSignature.getName(), lockKey);
            }
        }
        LOGGER.info("{}-{}, get lock fail:{}", joinPoint.getTarget().getClass().getName(), methodSignature.getName(), lockKey);
        if (!Strings.isNullOrEmpty(lockConfig.getLockFailMethod())) {
            return FallBackUtil.invokeFallbackMethod(joinPoint, lockFailMethod);
        }
        throw new LockFailException(joinPoint.getTarget().getClass().getName() + "--" + methodSignature.getName() + "...key=" + lockKey);
    }

    /**
     * 将注解转换为LockConfigEntity
     * @param annotation
     * @return
     */
    public abstract LockConfigFactoryParams params2LockConfig(ProceedingJoinPoint joinPoint, Annotation annotation);



}
