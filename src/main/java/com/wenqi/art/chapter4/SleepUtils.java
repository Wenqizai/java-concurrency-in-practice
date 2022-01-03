package com.wenqi.art.chapter4;

import java.util.concurrent.TimeUnit;

/**
 * @author Wenqi Liang
 * @date 2022/1/3
 */
public class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {

        }
    }
}
