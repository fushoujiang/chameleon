package org.fsj.chameleon.threadpool.entity;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 线程池参数
 */
public class ThreadPoolConfig {
    /**
     * 核心线程池参数
     */
    private int corePoolSize;

    /**
     * 最大线程池参数
     */
    private int maximumPoolSize;

    /**
     * 队列长度
     */
    private int workQueueSize;

     private long keepAliveTime;
     private TimeUnit unit;
     private BlockingQueue<Runnable> workQueue;
     private ThreadFactory threadFactory;
     private RejectedExecutionHandler handler;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public int getWorkQueueSize() {
        return workQueueSize;
    }

    public void setWorkQueueSize(int workQueueSize) {
        this.workQueueSize = workQueueSize;
    }
}
