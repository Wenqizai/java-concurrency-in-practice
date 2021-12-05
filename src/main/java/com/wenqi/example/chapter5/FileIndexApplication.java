package com.wenqi.example.chapter5;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 桌面搜索启动程序
 *
 * @author liangwenqi
 * @date 2021/9/16
 */
public class FileIndexApplication {

    public static void startIndexing(File[] roots) {
        // 消费者数量
        int consumerNum = 5;
        // 阻塞队列大小
        int queueSize = 100;

        BlockingQueue<File> queue = new LinkedBlockingDeque<>(queueSize);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        };

        for (File root : roots) {
            new Thread(new FileCrawler(queue, fileFilter, root)).start();
        }

        for (int i = 0; i < consumerNum; i++) {
            new Thread(new Indexer(queue)).start();
        }

    }
}
