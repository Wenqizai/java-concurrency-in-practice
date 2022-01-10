package com.wenqi.example.chapter13;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 避免死锁
 *
 * @author liangwenqi
 * @date 2022/1/10
 */
public class AvoidDeadlock {
    private Lock lock = new ReentrantLock(false);

    /**
     * 带有时间限制的加锁
     *
     * @param message
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public boolean trySendOnSharedLine(String message, long timeout, TimeUnit unit) throws InterruptedException {
        long nanosToLock = unit.toNanos(timeout) - estimateNanosToSend(message);

        // 未获取到锁
        if (!lock.tryLock(nanosToLock, TimeUnit.NANOSECONDS)) {
            return false;
        }

        try {
            return sendOnSharedLine(message);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 可中断的锁获取操作
     *
     * @param message
     * @return
     * @throws InterruptedException
     */
    public boolean trySendOnSharedLineInterrupt(String message) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            return cancellableSendOnShareLine(message);
        } finally {
            lock.unlock();
        }
    }


    /**
     * 向共享线路中发送一条消息
     *
     * @param message
     * @return
     */
    private boolean sendOnSharedLine(String message) {
        return false;
    }

    /**
     * 向共享线路中发送一条消息(可响应中断)
     *
     * @param message
     * @return
     */
    private boolean cancellableSendOnShareLine(String message) throws InterruptedException {
        return false;
    }

    private long estimateNanosToSend(String message) {
        return 0;
    }

}
