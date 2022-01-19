package com.wenqi.example.chapter14;

import net.jcip.annotations.ThreadSafe;

/**
 * 有界缓存处理前提条件不满足的情况三
 *
 * 睡眠 -> 唤醒 : 减少睡眠次数, 更加高效, 响应性高
 * @author liangwenqi
 * @date 2022/1/11
 */
@ThreadSafe
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {
    // 条件谓语: not-full (!isFull())
    // 条件谓语: not-empty (!isEmpty())

    public BoundedBuffer(int capacity) {
        super(capacity);
    }

    /**
     * 阻塞并直到: not-empty
     * @param v
     * @throws InterruptedException
     */
    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        doPut(v);
        // 唤醒全部, 则会唤醒了不需要唤醒的线程
        // 若使用notify(), 必须满足条件: 1. 所有等待线程的类型都相同  2. 单进单出, 在条件变量上的每次通知, 最多只能唤醒一个线程执行.
        notifyAll();
    }

    /**
     * 阻塞并直到: not-empty
     * @return
     * @throws InterruptedException
     */
    public synchronized V get() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        V v = doTake();
        // 唤醒全部, 则会唤醒了不需要唤醒的线程
        notifyAll();
        return v;
    }

}
