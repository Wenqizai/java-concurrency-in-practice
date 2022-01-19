package com.wenqi.example.chapter14;

import net.jcip.annotations.ThreadSafe;

/**
 * 有界缓存处理前提条件不满足的情况二
 * <p>
 * 阻塞一段时间后获取(吞吐量下降)
 *
 * @author liangwenqi
 * @date 2022/1/10
 */
@ThreadSafe
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    public SleepyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public void put(V v) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    return;
                }

                // 锁获取失败, 阻塞一段时间
                Thread.sleep(1000);
            }
        }
    }

    public V take() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isEmpty()) {
                    return doTake();
                }

                // 锁获取失败, 阻塞一段时间
                Thread.sleep(1000);
            }
        }
    }

}
