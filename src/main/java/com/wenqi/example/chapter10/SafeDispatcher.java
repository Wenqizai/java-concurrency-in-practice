package com.wenqi.example.chapter10;

import net.jcip.annotations.GuardedBy;

import java.util.HashSet;
import java.util.Set;

/**
 * @author liangwenqi
 * @date 2021/12/28
 */
public class SafeDispatcher {
    @GuardedBy("this")  private final Set<SafeTaxi> taxis;
    @GuardedBy("this")  private final Set<SafeTaxi> availableTaxis;

    public SafeDispatcher() {
        taxis = new HashSet<>();
        availableTaxis = new HashSet<>();
    }

    public synchronized void notifyAvailable(SafeTaxi taxi) {
        availableTaxis.add(taxi);
    }

    public Image getImage() {
        Set<SafeTaxi> copy;
        synchronized (this) {
            copy = new HashSet<>(taxis);
        }
        Image image = new Image();
        for (SafeTaxi taxi : copy) {
            image.drawMarker(taxi.getLocation());
        }
        return image;
    }
}
