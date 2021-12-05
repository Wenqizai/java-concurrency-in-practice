package com.wenqi.example.chapter7.item1;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 不可靠的取消操作将生产者至于阻塞的操作中,
 * 生产者永远不会检查取消标志, 导致任务一直执行, 不能中断
 *
 * @author liangwenqi
 * @date 2021/10/19
 */
public class BrokenPrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled = false;

    BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        try {
            while (!cancelled) {
                // 生产者生产速度大于消费者消费速度, 当消费者停止消费时, 此时队列还是满的, 导致生产者阻塞在put操作, 永远不会取检查取消标志cancelled, 进而线程不会停止
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        cancelled = true;
    }

    void consumerPrimes() throws InterruptedException {
        BlockingQueue<BigInteger> primes = new LinkedBlockingQueue<>();
        BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
        producer.start();
        try {
            while (true) {
                primes.take();
            }
        } finally {
            producer.cancel();
        }
    }
}