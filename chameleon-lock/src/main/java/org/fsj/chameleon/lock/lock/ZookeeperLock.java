package org.fsj.chameleon.lock.lock;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 基于zookeeper的分布式锁实现，使用apache curator的InterProcessMutex
 */
public class ZookeeperLock implements Lock {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperLock.class);
    private final String path;
    private InterProcessMutex interProcessMutex;

    public ZookeeperLock(CuratorFramework client, String path) {
        Preconditions.checkNotNull(client);
        Preconditions.checkArgument(StringUtils.isNotBlank(path));
        this.path = path;
        this.interProcessMutex = new InterProcessMutex(client,path);
    }

    @Override
    public void lock() {
        try {
            interProcessMutex.acquire();
        } catch (Exception e) {
            logger.error("the try lock by source zk occurs error",e);
            Throwables.propagate(e);
        }

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        try {
            interProcessMutex.acquire();
        } catch (Exception e) {
            logger.error("the lockInterruptibly by source zk occurs error",e);
            Throwables.propagate(e);
        }
    }

    @Override
    public boolean tryLock() {
        try {
            return tryLock(0L,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.error("the try lock get interrupted by source zk",e);
            return false;
        }
    }

    @Override
    public boolean tryLock(long waitTime, TimeUnit unit) throws InterruptedException {
        try {
            return interProcessMutex.acquire(waitTime,unit);
        } catch (Exception e) {
            logger.error("the try lock by source zk occurs error",e);
        }
        return false;
    }



    @Override
    public void unlock() {
        try {
            interProcessMutex.release();
        } catch (Exception e) {
            logger.error("the zk Lock unlock occurs error",e);
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("ZookeeperLock Unsupport condition");

    }
}
