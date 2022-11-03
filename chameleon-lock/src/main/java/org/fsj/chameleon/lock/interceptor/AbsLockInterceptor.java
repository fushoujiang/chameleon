package org.fsj.chameleon.lock.interceptor;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.fsj.chameleon.lang.util.FallBackUtil;
import org.fsj.chameleon.lock.entity.LockConfig;
import org.fsj.chameleon.lock.factory.AbsLockFactory;
import org.fsj.chameleon.lock.factory.LockConfigFactoryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public abstract class AbsLockInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbsLockInterceptor.class);

    AbsLockFactory absLockFactory;

    public Object lockAround(ProceedingJoinPoint joinPoint, Annotation annotation) throws Throwable{
        final LockConfigFactoryParams lockConfigEntity = params2LockConfig(joinPoint,annotation);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final LockConfig lockConfig = lockConfigEntity.getLockConfig();
        final String lockFailMethod = lockConfig.getLockFailMethod();
        Lock lock = absLockFactory.get(lockConfigEntity);
        if(!lock.tryLock(lockConfig.getTimeout(),TimeUnit.MILLISECONDS)){
            LOGGER.info("{}-{}, get lock fail:{}", joinPoint.getTarget().getClass().getName(), methodSignature.getName(), lockKey);
            return FallBackUtil.invokeFallbackMethod(joinPoint,lockFailMethod);
        }

        LOGGER.info("{}-{}, get lock success:{}", joinPoint.getTarget().getClass().getName(), methodSignature.getName(), lockKey);
        try {
            return joinPoint.proceed();
        }finally {
            lock.unlock();
            LOGGER.info("{}-{}, release lock:{}", joinPoint.getTarget().getClass().getName(), methodSignature.getName(), lockKey);
        }
    }

    /**
     * 将注解转换为LockConfigEntity
     * @param annotation
     * @return
     */
    public abstract LockConfigFactoryParams params2LockConfig(ProceedingJoinPoint joinPoint, Annotation annotation);




    /**
     * 解析加锁内容
     *
     * @param args            要加锁方法入参
     * @param lockConfigEntity 加锁配置（注解）
     * @return 要加锁的key
     */
    private String getLockKey(Object[] args, LockConfig lockConfigEntity) {
        StringBuilder lockKey = new StringBuilder(lockConfigEntity.getLockPrefix());
        String[] keys = lockConfigEntity.getKeys();
        int index = 0;
        for (int keyIndex : lockConfigEntity.getKeyIndexes()) {
            Object arg = args[keyIndex];
            String key = keys[index++];
            if (isBasicType(key)) {
                lockKey.append("_").append(arg);
            } else {
                lockKey.append("_").append(parseKey(arg, key));
            }
        }
        return lockKey.toString();
    }
    /**
     * 判断是否基础数据类型
     *
     * @param name 要判断的占位符
     * @return true/false
     */
    private boolean isBasicType(String name) {
        return StringUtils.equalsIgnoreCase(name, "LONG") || StringUtils.equalsIgnoreCase(name, "INT")
                || StringUtils.equalsIgnoreCase(name, "STRING");
    }

    /**
     * 解析非基础数据类型的占位符
     *
     * @param obj 占位符对应参数
     * @param key 占位符内容
     * @return 解析后的内容
     */
    private String parseKey(Object obj, String key) {
        String[] stirs = key.substring(1, key.length()).split("\\.");
        Object currObj = obj;
        for (String fieldName : stirs) {
            try {
                PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(obj.getClass(), fieldName);
                Method readMethod = propertyDescriptor.getReadMethod();
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                    readMethod.setAccessible(true);
                }
                currObj = readMethod.invoke(obj);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return String.valueOf(currObj);
    }






}
