package com.wenqi.example.chapter2;

/**
 * 可
 * @author liangwenqi
 * @date 2021/7/7
 */
public class ReentrantLockTest {
    public static void main(String[] args) {
        LoggingWidget loggingWidget = new LoggingWidget();
        int i = 0;
        while (i < 10) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    loggingWidget.doSomethingToo();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    loggingWidget.doSomethingToo();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            i++;
        }
    }
}

class Widget {
    public synchronized void doSomething() {
        System.out.println(Thread.currentThread().getName() + "->这是父锁...");
    }
}

class LoggingWidget extends Widget {
    public synchronized void doSomethingToo() {
        System.out.println(Thread.currentThread().getName() + "->这是子锁");
        super.doSomething();
    }
}
