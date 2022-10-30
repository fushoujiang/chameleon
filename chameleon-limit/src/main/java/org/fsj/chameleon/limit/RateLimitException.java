package org.fsj.chameleon.limit;

/**
 * @Description 接口限流异常
 * @Date 2021/2/26
 * @Created by fushoujiang
 */
public class RateLimitException extends RuntimeException {

    public RateLimitException() {
    }

    public RateLimitException(String message) {
        super(message);
    }

    public RateLimitException(Throwable cause) {
        super(cause);
    }
}