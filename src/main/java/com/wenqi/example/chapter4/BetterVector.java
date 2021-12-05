package com.wenqi.example.chapter4;

import net.jcip.annotations.ThreadSafe;

import java.util.Vector;

/**
 * 不存在则添加
 * 包装原始类, 保证原子操作
 *
 * @author liangwenqi
 * @date 2021/9/15
 */
@ThreadSafe
public class BetterVector<E> extends Vector<E> {
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !contains(x);
        if (absent)
            add(x);
        return absent;
    }

}
