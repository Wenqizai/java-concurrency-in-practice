package com.wenqi.example.chapter7.item1;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liangwenqi
 * @date 2021/11/25
 */
public class TimeRun {
    private static final ScheduledExecutorService cancelExec = new ScheduledThreadPoolExecutor(1);

    /**
     * 在外部线程中安排中断, 不能响应中断或不能及时中断
     *
     * @param r
     * @param timeout
     * @param unit
     */
    public static void timeRun(Runnable r, long timeout, TimeUnit unit) {
        final Thread taskThread = Thread.currentThread();
        cancelExec.schedule(taskThread::interrupt, timeout, unit);
        r.run();
    }

    /**
     * 在专门的线程中处理中断任务
     *
     * @param r
     * @param timeout
     * @param unit
     * @throws InterruptedException
     */
    public static void timeRunGood(final Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        class ReThrowableTask implements Runnable {
            private volatile Throwable t;

            @Override
            public void run() {
                try {
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }

            }

            void reThrow() {
                if (t != null) {
                    throw new RuntimeException("我把异常抛出来了");
                }
            }
        }

        ReThrowableTask task = new ReThrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        cancelExec.schedule(taskThread::interrupt, timeout, unit);
        taskThread.join(unit.toMillis(timeout));
        task.reThrow();
    }

    public static void main(String[] args) throws InterruptedException {
        timeRun(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, TimeUnit.MICROSECONDS);

        timeRunGood(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, TimeUnit.MICROSECONDS);
    }
}
