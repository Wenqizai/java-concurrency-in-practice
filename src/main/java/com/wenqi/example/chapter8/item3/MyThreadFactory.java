package com.wenqi.example.chapter8.item3;

import java.util.concurrent.ThreadFactory;

/**
 * 自定义线程工厂
 * @author liangwenqi
 * @date 2021/12/25
 */
public class MyThreadFactory implements ThreadFactory {
    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, poolName);
    }
}
