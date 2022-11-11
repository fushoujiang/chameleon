package org.fsj.chameleon.lock.factory;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.ZKPaths;
import org.fsj.chameleon.lang.factory.FactoryParams;
import org.fsj.chameleon.lock.entity.LockConfig;
import org.fsj.chameleon.lock.lock.ZookeeperLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;

public class ZookeeperLockFactory extends AbsLockFactory{
    private String ROOT  = "/chameleon/lock/";
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperLockFactory.class);
    private static final int SESSION_TIMEOUT = 30000;
    private static final int CONNECTION_TIMEOUT = 10000;
    private final CuratorFramework client;

    //构造器
    public ZookeeperLockFactory(String zkAddress){
        Preconditions.checkArgument(StringUtils.isNotBlank(zkAddress));
        CuratorFrameworkFactory.Builder builder  = CuratorFrameworkFactory.builder()
                .connectString(zkAddress).retryPolicy(new RetryNTimes(60, 1000))
                .connectionTimeoutMs(CONNECTION_TIMEOUT).sessionTimeoutMs(SESSION_TIMEOUT);
        client = builder.build();
        client.start();
        try {
            boolean connected = client.getZookeeperClient().blockUntilConnectedOrTimedOut();
            if(connected){
                LOGGER.info("the zk client connected ok");
                return;
            }
        } catch (InterruptedException e) {
            LOGGER.error("start zk client error, zk address:[{}]",zkAddress,e);
        }
        throw new IllegalStateException("error start zk Lock factory");
    }
    @Override
    public Lock createLock(FactoryParams<LockConfig> params) {
        return new ZookeeperLock(client,ZKPaths.makePath(ROOT,params.getCreateParams().getLockKey()));
    }
}
