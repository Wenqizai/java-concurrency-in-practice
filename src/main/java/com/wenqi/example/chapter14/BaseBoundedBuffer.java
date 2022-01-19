package com.wenqi.example.chapter14;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * 有界缓存实现的基类
 *
 * @author liangwenqi
 * @date 2022/1/10
 */
@ThreadSafe
public class BaseBoundedBuffer<V> {

    @GuardedBy("this") private final V[] buf;
    @GuardedBy("this") private int tail;
    @GuardedBy("this") private int head;
    @GuardedBy("this") private int count;

    protected BaseBoundedBuffer(int capacity) {
        this.buf = (V[]) new Object[capacity];
    }

    protected final synchronized void doPut(V v) {
        buf[tail] = v;
        if (++tail == buf.length) {
            tail = 0;
        }
        ++count;
    }

    protected final synchronized V doTake() {
        V v = buf[head];
        buf[head] = null;
        if (++head == buf.length) {
            head = 0;
        }
        --count;
        return v;
    }

    public final synchronized boolean isFull() {
        return count == buf.length;
    }

    public final synchronized boolean isEmpty() {
        return count == 0;
    }

}
