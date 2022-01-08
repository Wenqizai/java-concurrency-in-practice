package com.wenqi.art.chatper8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 控制连接数据库的连接数
 * 假设数据库最大连接数是10, 而我们的同时连接线程大于10就会出现部分连接失败的现象
 *
 * @author liangwenqi
 * @date 2022/1/8
 */
public class SemaphoreTest {
    /**
     * IO密集型任务, 线程数不妨设置大一点
     */
    private static final int THREAD_COUNT = 30;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
    private static Semaphore s = new Semaphore(10);

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        s.acquire();
                        System.out.println("save data");
                        Thread.sleep(1000);
                        s.release();
                        System.out.println();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
    }

}
