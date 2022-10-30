package org.fsj.chameleon.lang.cache;


import org.fsj.chameleon.lang.Refreshable;
import org.fsj.chameleon.lang.thread.DefaultThreadFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 刷新缓存管理器
 * @param <T>
 */
public class CacheRefreshManager<T> {


    private CacheRefreshManager() {
    }

    private static class CacheRefreshManagerHolder{
        private final static CacheRefreshManager instance = new CacheRefreshManager();
    }

    public static  CacheRefreshManager getInstance(){
        return CacheRefreshManagerHolder.instance;
    }
    private  final Set<Refreshable<T>> freshSet = new HashSet<>();

    private static final int POOL_SIZE =1;
    public static final ExecutorService UPDATE_CONFIG_EXECUTOR = new ThreadPoolExecutor(POOL_SIZE, POOL_SIZE, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), new DefaultThreadFactory("chameleon"));

    public  void fresh(T t){
        UPDATE_CONFIG_EXECUTOR.execute(() -> freshSet.forEach(s->s.refresh(t)));
    }

    public  void addRefreshListener(Refreshable<T> refreshable){
        freshSet.add(refreshable);
    }


}
