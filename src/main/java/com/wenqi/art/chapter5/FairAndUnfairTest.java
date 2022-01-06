package com.wenqi.art.chapter5;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liangwenqi
 * @date 2022/1/6
 */

public class FairAndUnfairTest {

    private static Lock fairLock = new ReentrantLockLock2(false);
    private static Lock unFairLock = new ReentrantLockLock2(true);

    public static void main(String[] args) throws InterruptedException {
        FairAndUnfairTest test = new FairAndUnfairTest();
        test.fair();

        Thread.sleep(1000);

        System.out.println("################################");

        test.unfair();
    }

    public void fair() {
        testLock(fairLock);
    }

    public void unfair() {
        testLock(unFairLock);
    }

    private void testLock(Lock lock) {
        for (int i = 0; i < 5; i++) {
            new Job((ReentrantLockLock2) lock).start();
        }
    }

    private static class Job extends Thread {
        private ReentrantLockLock2 lock;

        public Job(ReentrantLockLock2 lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " -> " +lock.getQueuedThreads());
            } finally {
                lock.unlock();
            }

        }
    }

    private static class ReentrantLockLock2 extends ReentrantLock {

        public ReentrantLockLock2(boolean fair) {
            super(fair);
        }

        @Override
        public Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }
}
