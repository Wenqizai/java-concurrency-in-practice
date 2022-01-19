package com.wenqi.example.chapter14;

import com.wenqi.example.chapter2.ReentrantLockTest;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.omg.CORBA.Object;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liangwenqi
 * @date 2022/1/11
 */
@ThreadSafe
public class ConditionBoundedBuffer<T> {
    protected final Lock lock = new ReentrantLock();
    /**
     * 条件谓词: notFull (count < items.length)
     */
    private final Condition notFull = lock.newCondition();
    /**
     * 条件谓词: notEmpty (count > 0)
     */
    private final Condition notEmpty = lock.newCondition();
    @GuardedBy("lock")
    private final T[] items = (T[]) new Object[1024];
    @GuardedBy("lock")
    private int tail, head, count;

    /**
     * 阻塞直到: notFull
     *
     * @param x
     * @throws InterruptedException
     */
    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[tail] = x;
            if (++tail == items.length) {
                tail = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 阻塞直到: notEmpty
     *
     * @return
     * @throws InterruptedException
     */
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            T x = items[head];
            items[head] = null;
            if (++head == items.length) {
                head = 0;
            }
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
