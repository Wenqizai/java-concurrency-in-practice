package com.wenqi.example.chapter9.item1;

/**
 * 顺序死锁
 * @author liangwenqi
 * @date 2021/12/27
 */
public class LeftRightDeadlock {
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight() {
        synchronized (left) {
            synchronized (right) {
                // doSomething()
            }
        }
    }

    public void rightLeft() {
        synchronized (right) {
            synchronized (left) {
                // doSomething()
            }
        }
    }

}
