package org.fsj.chameleon.limit.factory;


import com.google.common.util.concurrent.RateLimiter;
import org.fsj.chameleon.lang.ConfigManager;
import org.fsj.chameleon.lang.factory.FactoryParams;
import org.fsj.chameleon.limit.entity.RateLimiterConfig;
import org.fsj.chameleon.limit.limiter.CRateLimiter;
import org.fsj.chameleon.limit.limiter.GuavaRateLimiter;

public class GuavaRateLimiterFactory extends AbsRateLimiterFactory {


    public GuavaRateLimiterFactory(ConfigManager<RateLimiterConfig> manager) {
        super(manager);
    }

    public GuavaRateLimiterFactory() {
    }

    @Override
    public CRateLimiter createRateLimiter(RateLimiterConfig params) {
        return new GuavaRateLimiter(RateLimiter.create(params.getPerSecond()));
    }
}
