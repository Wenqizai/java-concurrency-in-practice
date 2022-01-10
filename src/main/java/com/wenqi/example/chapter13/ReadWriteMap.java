package com.wenqi.example.chapter13;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用读-写锁来包装Map
 *
 * 实际工作中使用高性能的ConcurrentHashMap来替代此方案, 除非想定制化Map
 *
 * @author liangwenqi
 * @date 2022/1/10
 */
public class ReadWriteMap<K, V> {
    private final Map<K, V> map;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock read = lock.readLock();
    private final Lock write = lock.writeLock();

    public ReadWriteMap(Map<K, V> map) {
        this.map = map;
    }

    public V put(K key, V value) {
        write.lock();
        try {
            return map.put(key, value);
        } finally {
            write.unlock();
        }
    }

    public V get(K key) {
        read.lock();
        try {
            return map.get(key);
        } finally {
            read.unlock();
        }
    }
}
