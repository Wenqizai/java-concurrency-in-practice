package com.wenqi.example.chapter5;

import net.jcip.annotations.GuardedBy;
import sun.reflect.generics.tree.VoidDescriptor;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 隐藏在字符串连接中的迭代操作
 *
 * @author liangwenqi
 * @date 2021/9/16
 */
public class HiddenIterator {

    public static void main(String[] args) throws InterruptedException {
        HiddenIterator hiddenIterator = new HiddenIterator();
        hiddenIterator.addThenThings();
    }

    @GuardedBy("this")
    private final Set<Integer> set = new HashSet<>();

    public synchronized void add(Integer i) {
        set.add(i);
    }

    public synchronized void remove(Integer i) {
        set.remove(i);
    }

    public void addThenThings() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            add(r.nextInt());
            System.out.println("DEBUG: added ten elements to " + set);
        }
    }

}
