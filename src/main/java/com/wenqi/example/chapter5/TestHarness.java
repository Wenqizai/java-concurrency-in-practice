package com.wenqi.example.chapter5;

import java.util.concurrent.CountDownLatch;

/**
 * @author liangwenqi
 * @date 2021/9/16
 */
public class TestHarness {

    public long timeTasks(int nThreads, final Runnable task) {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
            thread.start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        endGate.countDown();
        long end = System.nanoTime();
        return end - start;
    }

}
