package com.wenqi.example.chapter5;

import java.util.concurrent.BlockingQueue;
import javafx.concurrent.Task;

/**
 * @author liangwenqi
 * @date 2021/9/16
 */
public class TaskRunnable implements Runnable {

    BlockingQueue<Task> queue;

    @Override
    public void run() {
        try {
            processTask(queue.take());
        } catch (InterruptedException e) {
            // 恢复被中断的状态
            Thread.currentThread().interrupt();
        }
    }

    private void processTask(Task task) {

    }
}
