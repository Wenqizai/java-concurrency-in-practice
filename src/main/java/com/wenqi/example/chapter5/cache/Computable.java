package com.wenqi.example.chapter5.cache;

/**
 * @author liangwenqi
 * @date 2021/9/17
 */
public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
