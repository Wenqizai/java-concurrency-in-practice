package com.wenqi.example.chapter5;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * 消费者模式: 从阻塞队列中获取文件, 生成索引
 *
 * @author liangwenqi
 * @date 2021/9/16
 */
public class Indexer implements Runnable {

    private final BlockingQueue<File> queue;

    public Indexer(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                indexFile(queue.take());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void indexFile(File file) {

    }
}
