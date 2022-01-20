package com.wenqi.example.chapter15;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 通过CAS来谓词包含多个变量的不变性条件
 *
 * @author liangwenqi
 * @date 2022/1/20
 */
public class CasNumberRange {
    @Immutable
    private static class IntPair {
        // 不变性条件: lower <= upper
        final int lower;
        final int upper;

        public IntPair(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }
    }

    private final AtomicReference<IntPair> values = new AtomicReference<>(new IntPair(0, 0));

    public int getLower() {
        return values.get().lower;
    }

    public int getUpper() {
        return values.get().upper;
    }

    public void setLower(int i) {
        while (true) {
            IntPair old = values.get();
            if (i > old.upper) {
                throw new IllegalArgumentException("Can't set lower to " + i + "> upper");
            }
            IntPair newV = new IntPair(i, old.upper);
            if (values.compareAndSet(old, newV)) {
                return;
            }
        }
    }
}
