package com.wenqi.example.chapter7.item2;

import net.jcip.annotations.GuardedBy;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 多消费者单生产者模式
 * 单生产者保证日志记录时序, 多消费者保证日志消费速度
 * 可靠关闭的生产者-消费者日志服务
 *
 * @author liangwenqi
 * @date 2021/11/25
 */
public class LogService {
    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    @GuardedBy("this") private boolean isShutdown;
    @GuardedBy("this") private int reservations;

    public LogService(PrintWriter writer) {
        this.queue = new LinkedBlockingQueue<>(100);
        this.logger = new LoggerThread(writer);
    }

    public void start() {
        logger.start();
    }

    public void stop() {
        synchronized (this) {
            isShutdown = true;
        }
        logger.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown) {
                throw new IllegalStateException("logger is shutdown");
            } else {
                ++ reservations;
            }
        }
        queue.put(msg);
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
                    synchronized (LogService.this) {
                        if (isShutdown && reservations == 0) {
                            break;
                        }
                    }
                    String msg = queue.take();
                    synchronized (LogService.this) {
                        -- reservations;
                    }
                    writer.println(msg);
                }
            } catch (InterruptedException e) {
                // todo retry
                e.printStackTrace();
            } finally {
                writer.close();
            }
        }
    }
}
