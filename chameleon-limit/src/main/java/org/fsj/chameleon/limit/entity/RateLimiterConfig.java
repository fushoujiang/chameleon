package org.fsj.chameleon.limit.entity;

import java.util.concurrent.TimeUnit;

public class RateLimiterConfig {

    /**
     * 限流的组
     *
     * @return
     */
    private String group;
    /**
     * 限流的key
     *
     * @return
     */
    private String key;

    /**
     * 是否抛弃请求
     */
    private boolean wait;

    /**
     * 每秒向桶中放入令牌的数量   默认最大即不做限流
     *
     * @return
     */
    private int perSecond = Integer.MAX_VALUE;

    /**
     * 获取令牌的等待时间  默认0
     * isWait()==false生效
     *
     * @return
     */
    private int timeOut;
    /**
     * 超时时间单位
     *
     * @return
     */
    private TimeUnit timeOutUnit = TimeUnit.SECONDS;

    /**
     * 失败之后执行本类的方法名,若无则抛异常
     * <p>
     * 入参和返回值和增强方法需保持一致
     * </p>
     */
    private String failBackMethod;

    /**
     * 是否是集群模式
     * <p>
     * com.daojia.gunpowder.feature.limit.limiter.DRedissonRateLimiter：支持
     * com.daojia.gunpowder.feature.limit.limiter.GuavaRateLimiter：不支持
     * </p>
     */
    private boolean cluster;

    public String getGroup() {
        return group;
    }

    public RateLimiterConfig setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getKey() {
        return key;
    }

    public RateLimiterConfig setKey(String key) {
        this.key = key;
        return this;
    }

    public boolean isWait() {
        return wait;
    }

    public RateLimiterConfig setWait(boolean wait) {
        this.wait = wait;
        return this;
    }

    public int getPerSecond() {
        return perSecond;
    }

    public RateLimiterConfig setPerSecond(int perSecond) {
        this.perSecond = perSecond;
        return this;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public RateLimiterConfig setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public TimeUnit getTimeOutUnit() {
        return timeOutUnit;
    }

    public RateLimiterConfig setTimeOutUnit(TimeUnit timeOutUnit) {
        this.timeOutUnit = timeOutUnit;
        return this;
    }

    public String getFailBackMethod() {
        return failBackMethod;
    }

    public RateLimiterConfig setFailBackMethod(String failBackMethod) {
        this.failBackMethod = failBackMethod;
        return this;
    }

    public boolean isCluster() {
        return cluster;
    }

    public RateLimiterConfig setCluster(boolean cluster) {
        this.cluster = cluster;
        return this;
    }
}
