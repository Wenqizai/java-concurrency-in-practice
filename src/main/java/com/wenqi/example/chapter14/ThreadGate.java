package com.wenqi.example.chapter14;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * 使用wait和notifyAll来实现可重新关闭的阀门
 *
 * @author liangwenqi
 * @date 2022/1/11
 */
@ThreadSafe
public class ThreadGate {

    // 条件谓词: opened-since(n) (isOpen || generation > n)
    @GuardedBy("this")
    private boolean isOpen;
    @GuardedBy("this")
    private int generation;

    public synchronized void close() {
        isOpen = false;
    }

    public synchronized void open() {
        ++generation;
        isOpen = true;
        notifyAll();
    }

    /**
     * 阻塞并直到: opened-since(generation on entry)
     *
     * @throws InterruptedException
     */
    public synchronized void await() throws InterruptedException {
        int arrivalGeneration = generation;
        while (!isOpen && arrivalGeneration == generation) {
            wait();
        }
    }

}
