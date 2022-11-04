package org.fsj.chameleon.lock;

/**
 * 加锁失败异常
 */
public class LockFailException extends RuntimeException{
    public LockFailException() {
    }

    public LockFailException(String message) {
        super(message,null,false,false);
    }

    public LockFailException(String message, Throwable cause) {
        super(message,cause,false,false);
    }

    public LockFailException(Throwable cause) {
        super(null,cause,false,false);
    }
}
