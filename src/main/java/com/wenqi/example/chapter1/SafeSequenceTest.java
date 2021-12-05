package com.wenqi.example.chapter1;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

/**
 * @author liangwenqi
 * @date 2021/6/29
 */
public class SafeSequenceTest {

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


@ThreadSafe
class SafeSequence {
    @GuardedBy("this") private int value;

    public int getNext() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (SafeSequence.class) {
            value++;
        }
        return value;
    }
}


