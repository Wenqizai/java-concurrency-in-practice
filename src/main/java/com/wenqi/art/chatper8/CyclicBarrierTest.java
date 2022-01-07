package com.wenqi.art.chatper8;

import java.util.concurrent.CyclicBarrier;

/**
 * @author liangwenqi
 * @date 2022/1/7
 */
public class CyclicBarrierTest {
    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    System.out.println("new Thread...");
                    c.await();
                } catch (Exception e) {
                }
            }
        }).start();
        try {
            System.out.println("main....");
            c.await();
        } catch (Exception e) {
        }
        System.out.println(2);
    }
}