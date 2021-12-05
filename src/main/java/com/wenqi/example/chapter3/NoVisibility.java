package com.wenqi.example.chapter3;

/**
 * 可见性
 * @author liangwenqi
 * @date 2021/7/10
 */
public class NoVisibility {

    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        ready = true;
        number = 98;
    }

}
