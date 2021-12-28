package com.wenqi.example.chapter10.item1;

import net.jcip.annotations.GuardedBy;

import java.awt.Point;

/**
 * @author liangwenqi
 * @date 2021/12/28
 */
public class Image {
    @GuardedBy("this")  private Point location;

    public void drawMarker(Point location) {}
}
