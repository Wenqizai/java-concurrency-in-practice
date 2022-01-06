package com.wenqi.art.chapter5;

import com.wenqi.art.chapter4.Piped;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 同步工具: 同一时刻, 只允许至多两个线程同时访问, 超过两个线程的访问将会阻塞
 *
 * @author liangwenqi
 * @date 2022/1/6
 */
public class TwinsLock implements Lock {

    private static final class Sync extends AbstractQueuedSynchronizer {
        /**
         * state
         * <p>
         * 2 : 初始状态
         * 0 : 已经有两个线程获取到同步资源
         *
         * @param count
         */
        public Sync(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("count must large than zero");
            }
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int reduceCount) {
            for (; ; ) {
                int current = getState();
                int newCount = current - reduceCount;
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int returnCount) {
            for (; ; ) {
                int current = getState();
                int newCount = current + returnCount;
                if (compareAndSetState(current, newCount)) {
                    return true;
                }
            }
        }
    }

    private final Sync sync = new Sync(2);

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
