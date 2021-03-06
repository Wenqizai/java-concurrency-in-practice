package com.wenqi.art.chapter5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition构造有界队
 * <p>
 * 1. 当队列为空时，队列的获取操作将会阻塞获取线程，直到队列中有新增元素
 * 2. 当队列已满时，队列的插入操作将会阻塞插入线程，直到队列出现“空位”
 *
 * @author liangwenqi
 * @date 2022/1/7
 */
public class BoundedQueue<T> {
    private Object[] items;
    /**
     * 添加的表, 删除的下标和数组当前的数量
     */
    private int addIndex, removeIndex, count;
    private Lock lock = new ReentrantLock(false);
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public BoundedQueue(int size) {
        this.items = new Object[size];
    }

    /**
     * 添加一个元素, 如果数组满, 则添加线程进入等待状态, 直到有"空位"
     *
     * @param t
     * @throws InterruptedException
     */
    public void add(T t) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[addIndex] = t;
            if (++addIndex == items.length) {
                addIndex = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 由头部删除一个元素, 如果数组空, 则删除线程进入等待状态, 直到有新添加元素
     *
     * @return
     * @throws InterruptedException
     */
    @SuppressWarnings("unchecked")
    public T remove() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            Object item = items[removeIndex];
            if (++removeIndex == items.length) {
                removeIndex = 0;
            }
            --count;
            notFull.signal();
            return (T) item;
        } finally {
            lock.unlock();
        }
    }
}
