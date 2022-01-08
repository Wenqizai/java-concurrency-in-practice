package com.wenqi.art.chatper8;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author liangwenqi
 * @date 2022/1/8
 */
public class CyclicBarrierTest3 {
    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    System.out.println("await 1");
                }
            }
        });

        thread.start();
        thread.interrupt();

        try {
            c.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            // isBroken()方法用来了解阻塞的线程是否被中断
            System.out.println(c.isBroken());
        }

    }
}
