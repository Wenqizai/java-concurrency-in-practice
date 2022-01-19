package com.wenqi.example.chapter14;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 使用AbstractQueuedSynchronizer实现的二元闭锁
 *
 * @author liangwenqi
 * @date 2022/1/19
 */
public class OneShotLatch {
    private final Sync sync = new Sync();

    public void signal() {
        sync.releaseShared(0);
    }

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(0);
    }

    private static class Sync extends AbstractQueuedSynchronizer {
        /**
         * 当前占用线程
         */
        private volatile Thread owner;

        @Override
        protected int tryAcquireShared(int ignored) {
            // 如果闭锁是开的(state == 1), 那么这个操作将成功, 否则将失败
            return getState() == 1 ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int ignored) {
            // 现在打开闭锁
            setState(1);
            // 现在其他的线程可以获取改闭锁
            return true;
        }

        /**
         * 可重入获取锁
         *
         * @param ignored
         * @return
         */
        @Override
        protected boolean tryAcquire(int ignored) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, 1)) {
                    owner = current;
                    return true;
                }
            } else if (owner == current) {
                setState(c + 1);
                return true;
            }
            return false;
        }
    }
}
