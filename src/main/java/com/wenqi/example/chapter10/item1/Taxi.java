package com.wenqi.example.chapter10.item1;

import net.jcip.annotations.GuardedBy;

import java.awt.Point;
import java.util.Objects;

/**
 * 相互协作对象之间的锁顺序死锁
 *
 * com.wenqi.example.chapter10.item1.Taxi#setLocation(java.awt.Point) 先获取Taxi锁, 再获取Dispatcher锁
 * com.wenqi.example.chapter10.item1.Dispatcher#getImage()            先获取Dispatcher锁, 再获取Taxi锁
 *
 * 有几率发生死锁, 而且比较隐秘不易发现
 *
 * @author liangwenqi
 * @date 2021/12/28
 */
public class Taxi {
    @GuardedBy("this")  private Point location, destination;
    private final Dispatcher dispatcher;

    public Taxi(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public synchronized Point getLocation() {
        return location;
    }

    public synchronized void setLocation(Point location) {
        this.location = location;
        if (location.equals(destination)) {
            dispatcher.notifyAvailable(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taxi taxi = (Taxi) o;
        return Objects.equals(location, taxi.location) &&
                Objects.equals(destination, taxi.destination) &&
                Objects.equals(dispatcher, taxi.dispatcher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, destination, dispatcher);
    }
}
