package org.fsj.chameleon.limit.interceptor;

import com.google.common.base.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.fsj.chameleon.datasource.manager.AbsConfigManager;
import org.fsj.chameleon.limit.RateLimitException;
import org.fsj.chameleon.limit.entity.RateLimiterConfig;
import org.fsj.chameleon.limit.factory.AbsRateLimiterFactory;
import org.fsj.chameleon.limit.factory.GuavaRateLimiterFactory;
import org.fsj.chameleon.limit.factory.params.RateLimiterFactoryParams;
import org.fsj.chameleon.limit.limiter.CRateLimiter;
import org.fsj.chameleon.limit.manager.ApolloConfigManger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;


public abstract class AbsRateLimiterInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbsRateLimiterInterceptor.class);

    private AbsRateLimiterFactory absRateLimiterFactory;



    public AbsRateLimiterInterceptor(AbsRateLimiterFactory rateLimiterFactory) {
        this.absRateLimiterFactory = rateLimiterFactory;
    }
    public AbsRateLimiterInterceptor( ) {
        this.absRateLimiterFactory = new GuavaRateLimiterFactory();
    }

    public Object rateLimiterAround(ProceedingJoinPoint point, Annotation annotation) throws Throwable {
        final RateLimiterFactoryParams rateLimiterFactoryParams = params2RateLimiterConfig(point, annotation);
        final RateLimiterConfig limiterConfig = rateLimiterFactoryParams.getCreateParams();
        final CRateLimiter rateLimiter = absRateLimiterFactory.get(rateLimiterFactoryParams);

        if (Objects.isNull(rateLimiter)){
            LOGGER.debug("rateLimiterAround  no rateLimiter:{}", limiterConfig.getGroup());
            return point.proceed();
        }
        final String logKey = limiterConfig.getGroup();
        LOGGER.debug("rateLimiterAround params:{}", limiterConfig);
        if (limiterConfig.isWait()) {
            //阻塞获取令牌
            LOGGER.debug("rateLimiterAround acquire begin:{}", logKey);
            rateLimiter.acquire();
            LOGGER.debug("rateLimiterAround acquire got:{}", logKey);
            return point.proceed();
        }
        //非阻塞获取令牌
        LOGGER.debug("rateLimiterAround tryAcquire begin:{}", logKey);
        if (rateLimiter.tryAcquire(limiterConfig.getTimeOut(), limiterConfig.getTimeOutUnit())) {
            LOGGER.debug("rateLimiterAround tryAcquire got:{}", logKey);
            return point.proceed();
        }
        if (!Strings.isNullOrEmpty(limiterConfig.getFailBackMethod())) {
            LOGGER.debug("rateLimiterAround invoke failBackMethod:{}", logKey);
            return invokeFallbackMethod(point, limiterConfig.getFailBackMethod());
        }
        LOGGER.debug("rateLimiterAround throw ex:{}", logKey);
        throw new RateLimitException("【method】" + point.getSignature().getName() + "【params】" + Arrays.toString(point.getArgs()) + "called times >" + limiterConfig.getPerSecond() + "be limited");
    }


    /**
     * 切面或者注解获取对应的key或者对应的配置值
     *
     * @param point
     * @param annotation
     * @return
     */
    public abstract RateLimiterFactoryParams params2RateLimiterConfig(ProceedingJoinPoint point, Annotation annotation);

    protected Method findFallbackMethod(ProceedingJoinPoint joinPoint, String fallbackMethodName) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Method fallbackMethod = null;
        try {
            //这里通过判断必须取和原方法一样参数的fallback方法
            fallbackMethod = joinPoint.getTarget().getClass().getMethod(fallbackMethodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return fallbackMethod;
    }


    protected Object invokeFallbackMethod(ProceedingJoinPoint joinPoint, String fallback) {
        if ("nullFallbackMethod".equals(fallback)){
            return null;
        }
        Method method = findFallbackMethod(joinPoint, fallback);
        method.setAccessible(true);
        try {
            Object invoke = method.invoke(joinPoint.getTarget(), joinPoint.getArgs());
            return invoke;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RateLimitException(e);
        }
    }
}
