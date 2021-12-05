package com.wenqi.example.chapter5;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

/**
 * 生产者模式: 某个文件没有进行索引, 将其放入队列中, 等待消费者生成索引
 * @author liangwenqi
 * @date 2021/9/16
 */
public class FileCrawler implements Runnable {

    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File root;

    public FileCrawler(BlockingQueue<File> fileQueue, FileFilter fileFilter, File root) {
        this.fileQueue = fileQueue;
        this.fileFilter = fileFilter;
        this.root = root;
    }

    @Override
    public void run() {

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
