package org.fsj.chameleon.limit.interceptor;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * RateLimitInterceptor Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>9月 30, 2022</pre>
 */
public class RateLimitInterceptorTest {

    ApplicationContext context ;

    ThreadPoolExecutor executor ;
    @Before
    public void before() throws Exception {
        context = new AnnotationConfigApplicationContext(SpringConfig.class);
        executor =  new ThreadPoolExecutor(4,4,0, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1));

    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: around(ProceedingJoinPoint point, RateLimitAnnotation rateLimitAnnotation)
     */
    @Test
    public void testAround()  {
       LimitServiceImpl limitServiceImpl= context.getBean(LimitServiceImpl.class);






        for (int i=0;i<100;i++){
            try {
                limitServiceImpl.limit(i+"",i+"");
            }catch (Throwable e){
                e.printStackTrace();
            }
        }


    }

    /**
     * Method: annotation2RateLimiterConfig(Annotation annotation)
     */
    @Test
    public void testAnnotation2RateLimiterConfig() throws Exception {
//TODO: Test goes here... 
    }


} 
