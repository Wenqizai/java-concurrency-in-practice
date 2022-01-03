package com.wenqi.art.chapter4;

/**
 * @author Wenqi Liang
 * @date 2022/1/3
 */
public class Synchronized {

    public static void main(String[] args) {
        // 对Synchronized Class对象进行加锁
        synchronized (Synchronized.class) {
        }
// 静态同步方法，对Synchronized Class对象进行加锁
        m();
    }

    public static synchronized void m() {
    }
}
