package org.fsj.chameleon.datasource.manager;



public class LocalConfigManager extends AbsConfigManager {
    @Override
    public RateLimiterConfig loadFromDateSource(RateLimiterConfig rateLimiterConfig) {
        return rateLimiterConfig;
    }

}
