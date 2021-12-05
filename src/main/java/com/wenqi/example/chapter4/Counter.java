package com.wenqi.example.chapter4;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * @author liangwenqi
 * @date 2021/8/12
 */
@ThreadSafe
public class Counter {

    @GuardedBy("this") private long value = 0;

    public synchronized long getValue() {
        return value;
    }

    public synchronized long increment() {
        if (value == Long.MAX_VALUE) {
            throw new IllegalArgumentException("counter overflow");
        }
        return ++value;
    }

}
