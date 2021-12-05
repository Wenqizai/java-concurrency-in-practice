package com.wenqi.example.chapter5.cache;

import net.jcip.annotations.GuardedBy;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用HashMap和同步机制来初始化缓存
 *
 * compute不是线程安全, 需要synchronized保证线程同步, 但会极大降低性能
 *
 * @author liangwenqi
 * @date 2021/9/17
 */
public class Memorizer1<A, V> implements Computable<A, V> {

    @GuardedBy("this")
    private final Map<A, V> cache = new HashMap<>();
    private final Computable<A, V> c;

    public Memorizer1(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
