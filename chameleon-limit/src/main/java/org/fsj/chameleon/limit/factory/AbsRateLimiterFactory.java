package org.fsj.chameleon.limit.factory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.fsj.chameleon.lang.factory.AbsCacheFactory;
import org.fsj.chameleon.lang.factory.FactoryParams;
import org.fsj.chameleon.limit.entity.RateLimiterConfig;
import org.fsj.chameleon.limit.limiter.CRateLimiter;


public abstract class AbsRateLimiterFactory extends AbsCacheFactory<CRateLimiter,RateLimiterConfig> {


    public abstract CRateLimiter createRateLimiter(FactoryParams<RateLimiterConfig> params) ;

    @Override
    public CRateLimiter create(FactoryParams<RateLimiterConfig> params) {
        checkArguments(params.getCreateParams());
        return createRateLimiter(params);
    }



    private void checkArguments(RateLimiterConfig rateLimiterConfig ) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(rateLimiterConfig.getGroup()), "限流器分组group不能为空");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(rateLimiterConfig.getKey()), "限流器项目key不能为空");
    }







}
