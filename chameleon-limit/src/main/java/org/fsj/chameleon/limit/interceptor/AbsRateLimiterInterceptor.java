package org.fsj.chameleon.limit.interceptor;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import org.aspectj.lang.ProceedingJoinPoint;
import org.fsj.chameleon.lang.util.FallBackUtil;
import org.fsj.chameleon.limit.RateLimitException;
import org.fsj.chameleon.limit.entity.RateLimiterConfig;
import org.fsj.chameleon.limit.factory.AbsRateLimiterFactory;
import org.fsj.chameleon.limit.factory.GuavaRateLimiterFactory;
import org.fsj.chameleon.limit.factory.params.RateLimiterFactoryParams;
import org.fsj.chameleon.limit.limiter.CRateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
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

    public Object rateLimiterAround(ProceedingJoinPoint point, Annotation annotation) throws FlowException, Throwable {
        final RateLimiterFactoryParams rateLimiterFactoryParams = params2RateLimiterConfig(point, annotation);
        final RateLimiterConfig limiterConfig = rateLimiterFactoryParams.getCreateParams();
        final CRateLimiter rateLimiter = absRateLimiterFactory.get(rateLimiterFactoryParams);

        if (Objects.nonNull(rateLimiter)) {
            final String logKey = limiterConfig.getGroup();
            LOGGER.debug("rateLimiterAround acquire begin:{}", logKey);
            final boolean acquire = doAcquire(rateLimiter, limiterConfig);
            LOGGER.debug("rateLimiterAround acquire end:{},acquire:{}", logKey, acquire);
            if (!acquire) {
                if (!Strings.isNullOrEmpty(limiterConfig.getFailBackMethod())) {
                    LOGGER.debug("rateLimiterAround invoke failBackMethod:{}", logKey);
                    return FallBackUtil.invokeFallbackMethod(point, limiterConfig.getFailBackMethod());
                }
                LOGGER.debug("rateLimiterAround throw ex:{}", logKey);
                throw new RateLimitException("???method???" + point.getSignature().getName() + "???params???" + Arrays.toString(point.getArgs()) + "called times >" + limiterConfig.getPerSecond() + "be limited");
            }
        }
        return point.proceed();

    }


    private boolean doAcquire(CRateLimiter rateLimiter, RateLimiterConfig limiterConfig) throws BlockException {
        final String logKey = limiterConfig.getGroup();
        if (limiterConfig.isWait()) {
            //??????????????????
            rateLimiter.acquire();
            LOGGER.debug("rateLimiterAround acquire got:{}", logKey);
            return true;
        }
        return rateLimiter.tryAcquire(limiterConfig.getTimeOut(), limiterConfig.getTimeOutUnit());
    }


    /**
     * ?????????????????????????????????key????????????????????????
     *
     * @param point
     * @param annotation
     * @return
     */
    public abstract RateLimiterFactoryParams params2RateLimiterConfig(ProceedingJoinPoint point, Annotation annotation);


}
