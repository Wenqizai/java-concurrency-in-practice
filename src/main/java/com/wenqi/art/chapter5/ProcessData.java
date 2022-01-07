package com.wenqi.art.chapter5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 锁降级实例
 *
 * @author liangwenqi
 * @date 2022/1/6
 */
public class ProcessData {
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    static Lock readLock = rwl.readLock();
    static Lock writeLock = rwl.writeLock();

    private volatile boolean update;

    public ProcessData(boolean update) {
        this.update = update;
    }

    public static void main(String[] args) {
        new ProcessData(false).processData();
    }

    public void processData() {
        readLock.lock();
        if (!update) {
            // 必须先释放读锁 (不允许直接升级到读锁)
            readLock.unlock();
            // 锁降级从写锁获取到开始
            writeLock.lock();

            try {
                if (!update) {
                    // 准备数据流程
                    update = true;
                }
                readLock.lock();
            } finally {
                writeLock.unlock();
            }

            // 锁降级完成, 写锁降级为读锁
        }

        try {
            // 使用数据的流程
        } finally {
            readLock.unlock();
        }
    }
}
