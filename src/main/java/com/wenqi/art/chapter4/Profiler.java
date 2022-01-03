package com.wenqi.art.chapter4;

import java.util.concurrent.TimeUnit;

/**
 * @author Wenqi Liang
 * @date 2022/1/3
 */
public class Profiler {
    /**
     * 第一次get()方法调用时会进行初始化(如果set方法没有调用), 每个线程会调用一次
     */
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    public static void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static Long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) throws InterruptedException {
        Profiler.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost: " + Profiler.end() + "mills");
    }
}
