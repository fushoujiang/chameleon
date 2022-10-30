package org.fsj.chameleon.limit.convert;

import com.alibaba.fastjson.JSON;
import org.fsj.chameleon.lang.convert.AbstractConverter;
import org.fsj.chameleon.limit.entity.RateLimiterConfig;


public class JsonConvert extends AbstractConverter<String ,RateLimiterConfig> {
    public JsonConvert( ) {
        super(source-> JSON.parseObject(source,RateLimiterConfig.class),source->JSON.toJSONString(source));
    }
}
