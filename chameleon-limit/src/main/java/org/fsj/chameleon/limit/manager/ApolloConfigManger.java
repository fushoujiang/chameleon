package org.fsj.chameleon.limit.manager;

import com.ctrip.framework.apollo.Config;
import org.fsj.chameleon.datasource.manager.apollo.AbsApolloConfigManager;
import org.fsj.chameleon.limit.convert.ApolloChangeConvert;
import org.fsj.chameleon.limit.convert.JsonConvert;
import org.fsj.chameleon.limit.entity.RateLimiterConfig;
import org.fsj.chameleon.limit.factory.params.RateLimiterFactoryParams;


public class ApolloConfigManger extends AbsApolloConfigManager<RateLimiterConfig> {

    public ApolloConfigManger(Config config) {
        super(new RateLimiterFactoryParams(), config, new ApolloChangeConvert(), new JsonConvert());
    }

    @Override
    public String buildCacheKey(RateLimiterConfig rateLimiterConfig) {
        return rateLimiterConfig.getGroup()+"_"+rateLimiterConfig.getKey();
    }
}
