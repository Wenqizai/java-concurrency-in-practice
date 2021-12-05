package com.wenqi.example.chapter3;

import java.util.HashSet;
import java.util.Set;

/**
 * 发布对象的两种方式导致线程不安全
 * @author liangwenqi
 * @date 2021/7/10
 */
public class PublishObject {

    static class UnsafeField {
        public static Set<String> knownSecrets;

        public void initialize() {
            knownSecrets = new HashSet<>();
        }
    }

    class UnsafeStates {
        private String[] states = new String[] {
          "AK", "AL", "AM"
        };

        public String[] getStates() {
            return states;
        }
    }
}

/**
 * 不安全发布
 */
class UnsafePublish {
    public Holder holder;

    public void initialize() {
        holder = new Holder(42);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            int finalI = i;
            new Thread(() -> {
                new Holder(finalI).assertSanity();
                System.out.println(Thread.currentThread().getName() + "->" + finalI);
            }).start();

        }
    }
}

class Holder {
    private int n;

    public Holder(int n) {
        this.n = n;
    }

    public void assertSanity() {
        if (n != n) {
            throw new AssertionError("This statement is false.");
        }
    }
}
