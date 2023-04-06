package org.fsj.chameleon.threadpool.factory;

import org.fsj.chameleon.lang.factory.AbsCacheFactory;
import org.fsj.chameleon.threadpool.entity.ThreadPoolConfig;
import org.fsj.chameleon.threadpool.pool.ThreadPoolSupport;

public class ThreadPoolFactory extends AbsCacheFactory<ThreadPoolConfig,ThreadPoolSupport> {

    @Override
    public ThreadPoolSupport create(ThreadPoolConfig threadPoolConfig) {
        return null;
    }

    @Override
    public String getCacheKey(ThreadPoolConfig threadPoolConfig) {
        return null;
    }
}
