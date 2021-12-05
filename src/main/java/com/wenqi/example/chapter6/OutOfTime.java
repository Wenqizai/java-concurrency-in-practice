package com.wenqi.example.chapter6;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 错误的Timer行为
 *
 * @author liangwenqi
 * @date 2021/9/24
 */
public class OutOfTime {
    public static void main(String[] args) throws Exception {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 1);
        Thread.sleep(1000);
        timer.schedule(new ThrowTask(), 1);
        Thread.sleep(5000);
    }

    static class ThrowTask extends TimerTask {
        @Override
        public void run() {
            throw new RuntimeException();
        }
    }
}
