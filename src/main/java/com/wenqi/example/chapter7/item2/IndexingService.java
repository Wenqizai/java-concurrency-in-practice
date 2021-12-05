package com.wenqi.example.chapter7.item2;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * "毒丸"对象关闭服务
 * 意思: 当得到这个对象, 立即停止
 *
 * @author liangwenqi
 * @date 2021/11/26
 */
public class IndexingService {
    private static final File POISON = new File("");
    private final IndexerThread consumer = new IndexerThread();
    private final CrawlerThread producer = new CrawlerThread();
    private final BlockingQueue<File> fileQueue;
    private final File root;

    public IndexingService(BlockingQueue<File> fileQueue, File root) {
        this.fileQueue = fileQueue;
        this.root = root;
    }

    public void start() {
        consumer.start();
        producer.start();
    }

    public void stop() {
        producer.interrupt();
    }

    public void awaitTermination() throws InterruptedException {
        consumer.join();
    }

    class CrawlerThread extends Thread {

        @Override
        public void run() {
            try {
                crawl(root);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                while (true) {
                    try {
                        fileQueue.put(POISON);
                        break;
                    } catch (InterruptedException e) {
                        // 重试
                    }
                }
            }
        }

        private void crawl(File root) throws InterruptedException {
            File[] entries = root.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    if (entry.isDirectory()) {
                        crawl(entry);
                    }else if (Boolean.FALSE.equals(alreadyIndexed(entry))) {
                        fileQueue.put(root);
                    }
                }
            }
        }

        public Boolean alreadyIndexed(File entry) {
            return Boolean.FALSE;
        }
    }

    class IndexerThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    File file = fileQueue.take();
                    if (file == POISON) {
                        break;
                    } else {
                        indexFile(file);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void indexFile(File file) {}
    }
}
