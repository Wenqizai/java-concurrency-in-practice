package com.wenqi.art.chapter5;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁使用实例
 *
 * @author liangwenqi
 * @date 2022/1/6
 */
public class Cache {
    static Map<String, Object> map = new HashMap<>();
    static ReentrantReadWriteLock rwl  = new ReentrantReadWriteLock();
    static Lock r = rwl.readLock();
    static Lock w = rwl.writeLock();

    /**
     * 获取一个key对应的value
     * @param key
     * @return
     */
    public static final Object get(String key) {
        r.unlock();
        try {
            return map.get(key);
        } finally {
            r.unlock();
        }
    }

    /**
     * 设置key对应的value, 并返回旧的value
     *
     * @param key
     * @param value
     * @return
     */
    public static Object put(String key, Object value) {
        w.lock();
        try {
            return map.put(key, value);
        } finally {
            w.unlock();
        }
    }

    /**
     * 清空所有内容
     */
    public static final void clear() {
        w.lock();
        try {
            map.clear();
        } finally {
            w.unlock();
        }
    }
}
