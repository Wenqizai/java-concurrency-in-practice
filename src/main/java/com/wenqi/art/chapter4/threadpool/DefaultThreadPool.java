package com.wenqi.art.chapter4.threadpool;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Wenqi Liang
 * @date 2022/1/3
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {
    /**
     * 线程池最大限制数
     */
    private static final int MAX_WORKER_NUMBERS = 10;
    /**
     * 线程池默认的数量
     */
    private static final int DEFAULT_WORKER_NUMBERS = 10;
    /**
     * 线程池最小的数量
     */
    private static final int MIN_WORKER_NUMBERS = 1;
    /**
     * 工作队列, 将会向里面插入工作
     */
    private final LinkedList<Job> jobs = new LinkedList<>();
    /**
     * 工作者列表
     */
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());
    /**
     * 工作者线程数量
     */
    private static int workerNum = DEFAULT_WORKER_NUMBERS;
    /**
     * 线程编号
     */
    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool(int num) {
        workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : Math.max(num, MIN_WORKER_NUMBERS);
        initializeWorker(DEFAULT_WORKER_NUMBERS);
    }

    @Override
    public void execute(Job job) {
        if (job != null) {
            // 添加一个工作, 然后进行通知
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notifyAll();
            }
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker : workers) {
            worker.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs) {
            // 限制新增的Worker数量不能超过最大值
            if (num + workerNum > MAX_WORKER_NUMBERS) {
                num = MAX_WORKER_NUMBERS - workerNum;
            }
            initializeWorker(num);
            workerNum += num;
        }
    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs) {
            if (num >= workerNum) {
                throw new IllegalArgumentException("beyond workerNum");
            }
            // 按照给定的数量停止Worker
            int count = 0;
            while (count < num) {
                Worker worker = workers.get(count);
                if (workers.remove(worker)) {
                    count ++;
                }
            }
            workerNum -= count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    private void initializeWorker(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    /**
     * 工作者, 负责消费任务
     */
    class Worker implements Runnable {
        /**
         * 是否正在运行
         */
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
                    // 如果工作者队列是空, 那么wait
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            // 感知到外部对WorkerThread的终端操作, 返回
                            Thread.currentThread().interrupt();
                            return;
                        }

                        // 取出一个job
                        job = jobs.removeFirst();
                    }
                }

                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception e) {
                        // 忽略Job执行中的Exception
                    }
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }
}
