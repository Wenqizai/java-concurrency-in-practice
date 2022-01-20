package com.wenqi.example.chapter15;

import net.jcip.annotations.ThreadSafe;

/**
 * @author liangwenqi
 * @date 2022/1/20
 */
@ThreadSafe
public class CasCounter {
    private SimulatedCAS value;

    public int getValue() {
        return value.get();
    }

    public int increment() {
        int v;
        do {
            v = value.get();
        } while (v != value.compareAndSwap(v, v + 1));  // 注意这里是synchronize实现, 注意活锁问题, 更加严谨需要sleep
        return v + 1;
    }
}
