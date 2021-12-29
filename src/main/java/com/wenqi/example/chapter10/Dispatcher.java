package com.wenqi.example.chapter10;

import net.jcip.annotations.GuardedBy;

import java.util.HashSet;
import java.util.Set;

/**
 * @author liangwenqi
 * @date 2021/12/28
 */
public class Dispatcher {
    @GuardedBy("this")  private final Set<Taxi> taxis;
    @GuardedBy("this")  private final Set<Taxi> availableTaxis;

    public Dispatcher() {
        taxis = new HashSet<>();
        availableTaxis = new HashSet<>();
    }

    public synchronized void notifyAvailable(Taxi taxi) {
        availableTaxis.add(taxi);
    }

    public synchronized Image getImage() {
        Image image = new Image();
        for (Taxi taxi : taxis) {
            image.drawMarker(taxi.getLocation());
        }
        return image;
    }
}
