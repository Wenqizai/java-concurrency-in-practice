package com.wenqi.example.chapter11;

import java.util.concurrent.BlockingQueue;

/**
 * 任务队列的串行访问
 * 
 * @author liangwenqi
 * @date 2021/12/29
 */
public class WorkerThread extends Thread {

    private final BlockingQueue<Runnable> queue;

    public WorkerThread(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Runnable task = queue.take();
                task.run();
            } catch (InterruptedException e) {
                // 线程异常退出
                break;
            }
        }
    }
}
