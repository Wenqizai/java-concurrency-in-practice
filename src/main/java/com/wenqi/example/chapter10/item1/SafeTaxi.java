package com.wenqi.example.chapter10.item1;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.awt.Point;
import java.util.Objects;

/**
 * 通过公开调用来避免再相互协作的对象之间产生死锁
 * (引申出问题: 公开调用之后使得操作是非原子性的, 需留意非原子性操作是否会产生其他问题)
 *
 * @author liangwenqi
 * @date 2021/12/28
 */
@ThreadSafe
public class SafeTaxi {
    @GuardedBy("this")  private Point location, destination;
    private final SafeDispatcher dispatcher;

    public SafeTaxi(SafeDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public synchronized Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        boolean reachedDestination;
        synchronized (this) {
            this.location = location;
            reachedDestination = location.equals(destination);
        }
        if (reachedDestination) {
            dispatcher.notifyAvailable(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SafeTaxi safeTaxi = (SafeTaxi) o;
        return Objects.equals(location, safeTaxi.location) &&
                Objects.equals(destination, safeTaxi.destination) &&
                Objects.equals(dispatcher, safeTaxi.dispatcher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, destination, dispatcher);
    }
}
