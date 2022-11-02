package org.fsj.chameleon.monitor;

import com.sun.org.slf4j.internal.Logger;

import javax.management.*;

public class DynamicService {
    //
    // Implementation part.
    // This part gives the MBean the service functionality.
    //

    private boolean running;
    private int concurrent;

    public void start()
    {
        // Simulate the accept on incoming client requests
        // We will track how many requests we have, and if we pass a certain threshold,
        // we issue a notification.

        synchronized (this)
        {
            running = true;
        }

        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                simulateClientRequests();
            }
        });
        thread.start();
    }

    public void stop()
    {
        synchronized (this)
        {
            running = false;
        }
    }

    private void simulateClientRequests()
    {
        while (isRunning())
        {
            // Pick a time in ms to simulate the interval between incoming client requests
            long interval = Math.round(Math.random() * 1000L) + 1;
            try
            {
                Thread.sleep(interval);
            }
            catch (InterruptedException ignored)
            {
            }

            // Spawn a new Thread to accept the client request
            Thread thread = new Thread(new Runnable()
            {
                public void run()
                {
                    // Increase the number of concurrent clients
                    synchronized (DynamicService.this)
                    {
                        ++concurrent;
                        System.out.println("--DynamicService--" + Thread.currentThread() + "-- Incoming client request -- concurrent clients: " + concurrent);
                    }

                    // Pick a time in ms to simulate the processing of the client request
                    long processing = Math.round(Math.random() * 5000L) + 1;
                    try
                    {
                        Thread.sleep(processing);
                    }
                    catch (InterruptedException ignored)
                    {
                    }

                    // We're done with this client, decrease the number of concurrent clients
                    synchronized (DynamicService.this)
                    {
                        --concurrent;
                    }
                }
            });
            thread.start();
        }
    }

    public synchronized boolean isRunning()
    {
        return running;
    }

    public synchronized int getConcurrentClients()
    {
        return concurrent;
    }


    //
    // JMX part.
    // Note how short is :)
    //

    protected MBeanAttributeInfo[] createMBeanAttributeInfo()
    {
        return new MBeanAttributeInfo[]
                {
                        new MBeanAttributeInfo("Running", "boolean", "The running status of the DynamicService", true, false, true),
                        new MBeanAttributeInfo("ConcurrentClients", "int", "The number of concurrent clients", true, false, false)
                };
    }

    protected MBeanOperationInfo[] createMBeanOperationInfo()
    {
        return new MBeanOperationInfo[]
                {
                        new MBeanOperationInfo("start", "Starts the DynamicService", new MBeanParameterInfo[0], "void", MBeanOperationInfo.ACTION),
                        new MBeanOperationInfo("stop", "Stops the DynamicService", new MBeanParameterInfo[0], "void", MBeanOperationInfo.ACTION)
                };
    }
}
