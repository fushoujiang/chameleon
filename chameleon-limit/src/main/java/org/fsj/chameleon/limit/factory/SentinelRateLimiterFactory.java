package org.fsj.chameleon.limit.factory;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.fsj.chameleon.lang.factory.FactoryParams;
import org.fsj.chameleon.limit.entity.RateLimiterConfig;
import org.fsj.chameleon.limit.limiter.CRateLimiter;
import org.fsj.chameleon.limit.limiter.SentinelRateLimiter;

import java.util.Arrays;

public class SentinelRateLimiterFactory extends AbsRateLimiterFactory {

    @Override
    public CRateLimiter createRateLimiter(RateLimiterConfig createParams) {
        init(createParams);
        return new SentinelRateLimiter(createParams.getKey());
    }


    private void init(RateLimiterConfig rateLimiterConfig) {
        FlowRule flowRule = new FlowRule();
        flowRule.setResource(rateLimiterConfig.getKey());
        flowRule.setCount(rateLimiterConfig.getPerSecond());
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        flowRule.setLimitApp(rateLimiterConfig.getGroup());
         int toMillis = rateLimiterConfig.isWait()?
                 (int) rateLimiterConfig.getTimeOutUnit().toMillis(rateLimiterConfig.getTimeOut()) : Integer.MAX_VALUE;
        if (rateLimiterConfig.isWait()){
            flowRule.setMaxQueueingTimeMs(toMillis);
        }
        FlowRuleManager.loadRules(Arrays.asList(flowRule));
    }
}
