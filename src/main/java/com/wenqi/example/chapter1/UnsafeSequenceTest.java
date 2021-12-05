package com.wenqi.example.chapter1;

import net.jcip.annotations.NotThreadSafe;

/**
 * @author liangwenqi
 * @date 2021/6/29
 */
public class UnsafeSequenceTest {

    public static void main(String[] args) {
        UnsafeSequence unsafeSequence = new UnsafeSequence();
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                        System.out.println(unsafeSequence.getNext());
                }
            }).start();
        }
    }
}


@NotThreadSafe
class UnsafeSequence {
    private int value;

    public int getNext() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value++;
    }
}


