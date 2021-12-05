package com.wenqi.example.chapter7.item1;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * 通过线程中断的形式, 来中断任务
 * @author liangwenqi
 * @date 2021/10/19
 */
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;

        try {
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
            // 允许线程退出
        }
    }

    public void cancel() {interrupt();}

}
