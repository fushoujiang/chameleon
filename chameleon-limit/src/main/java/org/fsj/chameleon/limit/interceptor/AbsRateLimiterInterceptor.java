package org.fsj.chameleon.limit.interceptor;

import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.google.common.base.Strings;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.fsj.chameleon.lang.util.FallBackUtil;
import org.fsj.chameleon.limit.RateLimitException;
import org.fsj.chameleon.limit.entity.RateLimiterConfig;
import org.fsj.chameleon.limit.factory.AbsRateLimiterFactory;
import org.fsj.chameleon.limit.factory.GuavaRateLimiterFactory;
import org.fsj.chameleon.limit.factory.params.RateLimiterFactoryParams;
import org.fsj.chameleon.limit.limiter.CRateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import javax.management.monitor.GaugeMonitor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;


public abstract class AbsRateLimiterInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbsRateLimiterInterceptor.class);

    private AbsRateLimiterFactory absRateLimiterFactory;


    public AbsRateLimiterInterceptor(AbsRateLimiterFactory rateLimiterFactory) {
        this.absRateLimiterFactory = rateLimiterFactory;
    }

    public AbsRateLimiterInterceptor() {
        this.absRateLimiterFactory = new GuavaRateLimiterFactory();
    }

    public Object rateLimiterAround(ProceedingJoinPoint point, Annotation annotation) throws Throwable {
        final RateLimiterFactoryParams rateLimiterFactoryParams = params2RateLimiterConfig(point, annotation);
        final RateLimiterConfig limiterConfig = rateLimiterFactoryParams.getCreateParams();
        final CRateLimiter rateLimiter = absRateLimiterFactory.get(rateLimiterFactoryParams);

        if (Objects.isNull(rateLimiter)) {
            LOGGER.debug("rateLimiterAround  no rateLimiter:{}", limiterConfig.getGroup());
            return point.proceed();
        }
        final String logKey = limiterConfig.getGroup();

        try {
            LOGGER.debug("rateLimiterAround acquire begin:{}", logKey);
            doAcquire(rateLimiter,limiterConfig);
            LOGGER.debug("rateLimiterAround acquire end:{}", logKey);
        }catch (FlowException flowException){
            //ignore flowException 目前支持自己的限流降级，熔断相关的异常抛到上层处理，目前不处理。
        }
        if (!Strings.isNullOrEmpty(limiterConfig.getFailBackMethod())) {
            LOGGER.debug("rateLimiterAround invoke failBackMethod:{}", logKey);
            return FallBackUtil.invokeFallbackMethod(point, limiterConfig.getFailBackMethod());
        }
        LOGGER.debug("rateLimiterAround throw ex:{}", logKey);
        throw new RateLimitException("【method】" + point.getSignature().getName() + "【params】" + Arrays.toString(point.getArgs()) + "called times >" + limiterConfig.getPerSecond() + "be limited");
    }


    private boolean doAcquire(CRateLimiter rateLimiter, RateLimiterConfig limiterConfig) throws Throwable {
        final String logKey = limiterConfig.getGroup();
        if (limiterConfig.isWait()) {
            //阻塞获取令牌
            rateLimiter.acquire();
            LOGGER.debug("rateLimiterAround acquire got:{}", logKey);
            return true;
        }
        return rateLimiter.tryAcquire(limiterConfig.getTimeOut(), limiterConfig.getTimeOutUnit());
    }


    /**
     * 切面或者注解获取对应的key或者对应的配置值
     *
     * @param point
     * @param annotation
     * @return
     */
    public abstract RateLimiterFactoryParams params2RateLimiterConfig(ProceedingJoinPoint point, Annotation annotation);


}
