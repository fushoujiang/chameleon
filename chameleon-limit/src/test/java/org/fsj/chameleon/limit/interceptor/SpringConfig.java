package org.fsj.chameleon.limit.interceptor;

import org.fsj.chameleon.lang.ConfigManager;
import org.fsj.chameleon.limit.factory.AbsRateLimiterFactory;
import org.fsj.chameleon.limit.factory.GuavaRateLimiterFactory;
import org.fsj.chameleon.limit.factory.RedissonRateLimiterFactory;
import org.fsj.chameleon.limit.factory.SentinelRateLimiterFactory;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class SpringConfig {



    @Bean
    public RateLimitInterceptor rateLimitInterceptor(AbsRateLimiterFactory rateLimiterFactory){
        return new RateLimitInterceptor(rateLimiterFactory);
    }



    @Bean
    public AbsRateLimiterFactory rateLimiterFactory(){
        return new SentinelRateLimiterFactory();
    }



    public  RedissonClient redisson(Config config){
        return  Redisson.create(config);
    }
    public AbsRateLimiterFactory rateLimiterFactory(RedissonClient redissonClient){
        return new RedissonRateLimiterFactory(redissonClient);
    }
    public  Config config(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return config;
    }
    @Bean
    public LimitServiceImpl limitService(){
        return new LimitServiceImpl();
    }


}
