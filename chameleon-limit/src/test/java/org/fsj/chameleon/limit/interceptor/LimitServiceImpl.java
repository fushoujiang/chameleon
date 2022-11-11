package org.fsj.chameleon.limit.interceptor;

import org.fsj.chameleon.limit.RateLimitAnnotation;
import org.springframework.stereotype.Service;

import javax.management.*;
import javax.management.monitor.GaugeMonitor;

/**
 * 订单业务接口
 * @author wenqi.wu
 */
@Service
public class LimitServiceImpl {

    @RateLimitAnnotation(group = "test",key = "key" ,perSecond = 1,isWait = true)
    public boolean limit(String name, String mobile) {
        Thread thread  = Thread.currentThread();
        System.out.println(thread.getName()+"name:"+name+"mobile:"+mobile);
        return true;
    }




    public static void main(String[] args) throws Exception {
        // Let's create the MBeanServer
        MBeanServer server = MBeanServerFactory.newMBeanServer();

        // Let's create a dynamic MBean and register it
        Object serviceMBean = new Object();
        ObjectName serviceName = new ObjectName("examples", "mbean", "dynamic");
        server.registerMBean(serviceMBean, serviceName);

        // Now let's register a Monitor
        // We would like to know if we have peaks in activity, so we can use JMX's
        // GaugeMonitor
        GaugeMonitor monitorMBean = new GaugeMonitor();
        ObjectName monitorName = new ObjectName("examples", "monitor", "gauge");
        server.registerMBean(monitorMBean, monitorName);

        // Setup the monitor: we want to be notified if we have too many clients or too less
        monitorMBean.setThresholds(new Integer(8), new Integer(4));
        // Setup the monitor: we want to know if a threshold is exceeded
        monitorMBean.setNotifyHigh(true);
        monitorMBean.setNotifyLow(true);
        // Setup the monitor: we're interested in absolute values of the number of clients
        monitorMBean.setDifferenceMode(false);
        // Setup the monitor: link to the service MBean
        monitorMBean.addObservedObject(serviceName);
        monitorMBean.setObservedAttribute("ConcurrentClients");
        // Setup the monitor: a short granularity period
        monitorMBean.setGranularityPeriod(50L);
        // Setup the monitor: register a listener
        monitorMBean.addNotificationListener(new NotificationListener()
        {
            public void handleNotification(Notification notification, Object handback)
            {
                System.out.println(notification);
            }
        }, null, null);
        // Setup the monitor: start it
        monitorMBean.start();

    }

}
