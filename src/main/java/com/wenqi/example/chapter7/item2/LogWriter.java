package com.wenqi.example.chapter7.item2;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 多消费者单生产者模式
 * 单生产者保证日志记录时序, 多消费者保证日志消费速度
 * 不支持关闭的生产者-消费者日志服务
 *
 * @author liangwenqi
 * @date 2021/11/25
 */
public class LogWriter {
    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    private boolean shutdownRequested = false;

    public LogWriter(PrintWriter writer) {
        this.queue = new LinkedBlockingQueue<>(100);
        this.logger = new LoggerThread(writer);
    }

    public void start() {
        logger.start();
    }

    public void log(String msg) throws InterruptedException {
        if (!shutdownRequested) {
            queue.put(msg);
        } else {
            throw new IllegalStateException("logger is shutdown");
        }
    }

    private class LoggerThread extends Thread {
        private final PrintWriter writer;

        public LoggerThread(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    writer.println(queue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 借助标志位来关闭日志服务, 但是这并不是可靠的
                shutdownRequested = true;
                // interrupt之后日志消费关闭, 会丢失那些正在等待写入到日志的信息
                // 其他线程调用log时将会被阻塞, 因为日志队列是满的, 而这种状态无法解除
                writer.close();
            }
        }
    }
}
