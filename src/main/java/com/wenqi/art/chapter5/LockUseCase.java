package com.wenqi.art.chapter5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liangwenqi
 * @date 2022/1/5
 */
public class LockUseCase {
    Lock lock = new ReentrantLock(false);

    public void lock() {
        // lock()不能放在try里面, 如果try不成功, finally执行后会导致其他线程的锁被释放
        lock.lock();

        // 此方法是非阻塞地获取锁
        //lock.tryLock();
        try {
            System.out.println("doSomething....");
        } finally {
            lock.unlock();
        }
    }
}
