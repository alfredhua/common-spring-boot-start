package com.common.redis;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @author guozhenhua
 * @date 2020/07/11
 */
public class RedisLockUtils {

    RedissonClient redissonClient;

    private static final String PREX_LOAK="redis_lock:";


    public RedisLockUtils(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public RLock lock(String key){
        RLock lock = redissonClient.getLock(PREX_LOAK+key);
        lock.lock();
        return lock;
    }


    public RLock lock(String lockKey, int leaseTime) {
        RLock lock = redissonClient.getLock(PREX_LOAK+lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    public RLock lock(String lockKey, TimeUnit unit ,int timeout) {
        RLock lock = redissonClient.getLock(PREX_LOAK+lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(PREX_LOAK+lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(PREX_LOAK+lockKey);
        lock.unlock();
    }

    public void unlock(RLock lock) {
        lock.unlock();
    }




}
