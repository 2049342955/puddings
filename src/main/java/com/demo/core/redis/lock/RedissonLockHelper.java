package com.demo.core.redis.lock;

import java.util.concurrent.TimeUnit;

import com.demo.core.exception.error.code.BaseErrorsEnum;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

public class RedissonLockHelper {
    private RedissonClient redisson;

    public RedissonLockHelper(RedissonClient redisson) {
        this.redisson = redisson;
    }

    public void setRedisson(RedissonClient redisson) {
        this.redisson = redisson;
    }

    public RLock getRLock(String key) {
        return this.redisson.getLock(key);
    }

    public void tryLock(String key, long leaseTime, TimeUnit timeUnit) {
        try {
            this.getRLock(key).tryLock(leaseTime, timeUnit);
        } catch (InterruptedException var6) {
            var6.printStackTrace();
            throw BaseErrorsEnum.RLOCK_TRY_LOCK_ERROR.exception;
        }
    }

    public void tryLock(String key, long waitTime, long leaseTime, TimeUnit timeUnit) {
        try {
            this.getRLock(key).tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException var8) {
            var8.printStackTrace();
            throw BaseErrorsEnum.RLOCK_TRY_LOCK_ERROR.exception;
        }
    }

    public boolean isLock(String key) {
        return this.getRLock(key).isLocked();
    }

    public void isLockThrowException(String key) {
        if (this.getRLock(key).isLocked()) {
            throw BaseErrorsEnum.RLOCK_HAS_LOCKED.exception;
        }
    }

    public void unLock(String key) {
        this.getRLock(key).unlock();
    }
}
