package com.wenqi.example.chapter7.item2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 关闭适用ExecutorService的日志服务
 * @author liangwenqi
 * @date 2021/11/26
 */
public class LogServiceExecutor {
    private final ExecutorService exec = Executors.newSingleThreadExecutor();

    public void stop() {
        try {
            exec.shutdown();
            exec.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭流资源
        }

    }
}
