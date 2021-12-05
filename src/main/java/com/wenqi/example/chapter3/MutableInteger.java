package com.wenqi.example.chapter3;

import net.jcip.annotations.NotThreadSafe;

/**
 * 线程不安全的Bean
 * @author liangwenqi
 * @date 2021/7/10
 */
@NotThreadSafe
public class MutableInteger {

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
