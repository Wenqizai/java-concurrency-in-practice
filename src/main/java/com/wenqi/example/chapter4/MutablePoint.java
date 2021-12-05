package com.wenqi.example.chapter4;

import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * @author liangwenqi
 * @date 2021/9/14
 */
@Immutable
public class MutablePoint {
    public int x, y;

    public MutablePoint() {
        x = 0;
        y = 0;
    }

    public MutablePoint(MutablePoint p) {
        this.x = p.x;
        this.y = p.y;
    }

}
