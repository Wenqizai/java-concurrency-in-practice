package com.wenqi.art.chapter4;

/**
 * @author Wenqi Liang
 * @date 2022/1/3
 */
public class Daemon {
    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);
        thread.start();
    }

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                SleepUtils.second(10);
            } finally {
                // JVM退出时, Daemon线程不一定会执行finally
                System.out.println("DaemonThread finally run.");
            }
        }
    }
}
