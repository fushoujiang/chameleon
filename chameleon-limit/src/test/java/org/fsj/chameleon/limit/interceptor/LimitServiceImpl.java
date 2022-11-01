package org.fsj.chameleon.limit.interceptor;

import org.fsj.chameleon.limit.RateLimitAnnotation;
import org.springframework.stereotype.Service;

/**
 * 订单业务接口
 * @author wenqi.wu
 */
@Service
public class LimitServiceImpl {

    @RateLimitAnnotation(group = "test",key = "key" ,perSecond = 1)
    public boolean limit(String name, String mobile) {
        Thread thread  = Thread.currentThread();
        System.out.println(thread.getName()+"name:"+name+"mobile:"+mobile);
        return true;
    }
}
