package com.wenqi.example.chapter5;

import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;
import javafx.concurrent.Task;

import java.util.concurrent.BlockingQueue;

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
