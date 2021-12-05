package com.wenqi.example.chapter7.item1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author liangwenqi
 * @date 2021/11/25
 */
public class TimeRunFuture {
    private static final ScheduledExecutorService cancelExec = new ScheduledThreadPoolExecutor(1);

    /**
     * 通过Future来取消任务
     *
     * 当future.get()抛出InterruptedException/TimeoutException时, 如果我们知道不需要结果了,
     * 那么就可以调用Future.cancel()来取消任务
     * @param r
     * @param timeout
     * @param unit
     */
    public static void timedRun(Runnable r, long timeout, TimeUnit unit) {
        Future<?> task = cancelExec.submit(r);
        try {
            task.get(timeout, unit);
        } catch (InterruptedException | ExecutionException e) {
            // 如果在任务中抛出异常, 那么重新抛出该异常
            throw new RuntimeException("自定义异常");
        }
        catch (TimeoutException e) {
            // 接下来任务将被取消
            e.printStackTrace();
        } finally {
            // 如果任务已经结束, 那么执行取消操作也不会带来任何影响
            // 如果任务正在运行, 那么将被中断
            task.cancel(true);
        }
    }

    public static void main(String[] args) {
        timedRun(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("interrupt");
                e.printStackTrace();
            }
        }, 1, TimeUnit.SECONDS);
    }
}
