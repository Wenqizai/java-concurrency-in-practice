package com.wenqi.art.chatper8;

/**
 * @author liangwenqi
 * @date 2022/1/7
 */
public class JoinCountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        Thread parse1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("");
            }
        });

        Thread parse2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("");
            }
        });

        parse1.start();
        parse2.start();
        parse1.join();
        parse2.join();
    }
}
