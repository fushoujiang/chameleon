package org.fsj.chameleon.limit.interceptor;

import com.google.common.base.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.fsj.chameleon.limit.RateLimitAnnotation;
import org.fsj.chameleon.limit.RateLimitException;
import org.fsj.chameleon.limit.entity.RateLimiterConfig;
import org.fsj.chameleon.limit.factory.AbsRateLimiterFactory;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;


@Aspect
public class RateLimitInterceptor extends AbsRateLimiterInterceptor {

    private static final int MAX_KEY_LENGTH = 50;

    public RateLimitInterceptor(AbsRateLimiterFactory rateLimiterFactory) {
        super(rateLimiterFactory);
    }

    public RateLimitInterceptor() {
    }

    @Around("@annotation(rateLimitAnnotation)")
    public Object around(ProceedingJoinPoint point, RateLimitAnnotation rateLimitAnnotation) throws Throwable {
        return rateLimiterAround(point, rateLimitAnnotation);
    }

    @Override
    public RateLimiterConfig params2RateLimiterConfig(ProceedingJoinPoint point, Annotation annotation) {
        RateLimitAnnotation rateLimitAnnotation = (RateLimitAnnotation) annotation;
        String key = rateLimitAnnotation.key();
        String group = rateLimitAnnotation.group();
        //如果没注解没有配置则使用类名+方法名称
        if (Strings.isNullOrEmpty(group)) {
            String methodName = point.getSignature().getName();
            String[] split = StringUtils.split(point.getSignature().getDeclaringTypeName(), ".");
            key = split[split.length - 1] + "." + methodName;
        }
        if (Strings.isNullOrEmpty(group)) {
            throw new RateLimitException("注解上缺少group配置");
        }
        //key长度不超过50
        if (key.length() > MAX_KEY_LENGTH) {
            key = key.substring(key.length() - MAX_KEY_LENGTH);
        }
        return new RateLimiterConfig().setCluster(rateLimitAnnotation.cluster())
                .setFailBackMethod(rateLimitAnnotation.failBackMethod())
                .setTimeOut(rateLimitAnnotation.timeOut())
                .setTimeOutUnit(rateLimitAnnotation.timeOutUnit())
                .setPerSecond(rateLimitAnnotation.perSecond())
                .setWait(rateLimitAnnotation.isWait())
                .setKey(key)
                .setGroup(group);

    }
}
