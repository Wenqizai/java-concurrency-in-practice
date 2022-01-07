package com.wenqi.art.chapter6;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author liangwenqi
 * @date 2022/1/7
 */
public class HashMapDeadLoop {
    public static void main(String[] args) throws InterruptedException {
        // 此代码要在JDK7环境下才有可能生效
        final Map<String, String> map = new HashMap<>(2);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            map.put(UUID.randomUUID().toString(), "");
                        }
                    }, "ftf" + i).start();
                }
            }
        }, "ftf");
        t.start();
        t.join();
    }
}
