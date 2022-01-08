package com.wenqi.art.chatper8;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liangwenqi
 * @date 2022/1/8
 */
public class ExchangerTest {
    private static final Exchanger<String> exgr = new Exchanger<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // A银行录入的流水数据
                    String A = "银行流水A";
                    exgr.exchange(A);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // B银行录入的流水数据
                    String B = "银行流水B";
                    String A = exgr.exchange("A");

                    System.out.println("A和B数据是否一致: " + A.equals(B) + ", A录入的是: " + A + ", B录入是: " + B);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadPool.shutdown();
    }
}
