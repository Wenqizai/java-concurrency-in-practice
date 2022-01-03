package com.wenqi.art.chapter1;

/**
 * @author Wenqi Liang
 * @date 2022/1/3
 */
public class DeadlockDemo {
    private static String A = "A";
    private static String B = "B";

    public static void main(String[] args) {
        new DeadlockDemo().deadlock();
    }

    private void deadlock() {
        Thread b = new Thread(() -> {
            synchronized (A) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B) {
                    System.out.println("B");
                }
            }
        });
        Thread a = new Thread(() -> {
            synchronized (B) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (A) {
                    System.out.println("A");
                }
            }
        });
        a.start();
        b.start();
    }
}
