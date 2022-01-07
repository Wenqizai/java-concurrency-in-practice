package com.wenqi.art.chapter6;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author liangwenqi
 * @date 2022/1/7
 */
public class CountTask extends RecursiveTask<Integer> {
    /**
     * 阈值
     */
    private static final int THRESHOLD = 2;
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        // 如果任务足够小就计算任务
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 如果任务大于阈值, 就分裂成两个子任务计算
            int middle = (start + end) / 2;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);
            // 执行子任务
            leftTask.fork();
            rightTask.fork();
            // 等待子任务执行完成, 并得到其结果
            Integer leftResult = leftTask.join();
            Integer rightResult = rightTask.join();
            // 合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 生成一个计算任务, 负责计算1+2+3+4
        CountTask task = new CountTask(1, 4);
        // 执行一个任务
        ForkJoinTask<Integer> result = forkJoinPool.submit(task);

        // 任务运行过程中发生异常, 此方法可获取异常
        if (task.isCompletedAbnormally()) {
            System.out.println(task.getException());
        }

        try {
            System.out.println(result.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
