package org.fsj.chameleon.lock.entity;

public class LockConfig {
    /**
     * 要加锁的key，有特定规则，和${@code keyIndexes}对应，用于解析keyIndex对应的值
     */
    private String[] keys;

    /**
     * 要加锁的key的index，标识key所在的参数位置
     */
    private int[] keyIndexes;
    /**
     * 获取锁超时等待时间，单位为毫秒。
     *
     * <p>
     * 依赖具体分布式锁实现是否支持timeout
     * </p>
     */
    private int timeout;

    /**
     * 公平/非公平
     */
    private boolean fair;
    /**
     * lock 项目名称，单个集群保证唯一
     */
    private String project;
    /**
     * lock 前缀需要保持唯一，若无则以方法名
     */
    private String lockPrefix;

    /**
     * 失败之后执行本类的方法名,若无则抛异常
     * <p>
     * 入参和返回值和增强方法需保持一致
     * </p>
     */
    private String lockFailMethod;


    /**
     * 最终key=lockPrefix+keys解析参数
     */
    private String lockKey;


    public String[] getKeys() {
        return keys;
    }

    public LockConfig setKeys(String[] keys) {
        this.keys = keys;
        return this;
    }


    public String getLockKey() {
        return lockKey;
    }

    public void setLockKey(String lockKey) {
        this.lockKey = lockKey;
    }

    public int[] getKeyIndexes() {
        return keyIndexes;
    }

    public LockConfig setKeyIndexes(int[] keyIndexes) {
        this.keyIndexes = keyIndexes;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public LockConfig setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public String getProject() {
        return project;
    }

    public LockConfig setProject(String project) {
        this.project = project;
        return this;
    }

    public String getLockPrefix() {
        return lockPrefix;
    }

    public LockConfig setLockPrefix(String lockPrefix) {
        this.lockPrefix = lockPrefix;
        return this;
    }

    public String getLockFailMethod() {
        return lockFailMethod;
    }

    public LockConfig setLockFailMethod(String lockFailMethod) {
        this.lockFailMethod = lockFailMethod;
        return this;
    }

    public boolean isFair() {
        return fair;
    }

    public LockConfig setFair(boolean fair) {
        this.fair = fair;
        return this;
    }

}
