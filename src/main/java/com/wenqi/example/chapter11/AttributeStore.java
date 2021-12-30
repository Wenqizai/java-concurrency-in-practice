package com.wenqi.example.chapter11;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 减少锁的粒度
 *
 * @author liangwenqi
 * @date 2021/12/29
 */
@ThreadSafe
public class AttributeStore {
    @GuardedBy("this")
    private final Map<String, String> attributes = new HashMap<>();

    /**
     * 线程安全, synchronized修饰范围过大, 实际只有Map.get()才真正需要锁
     *
     * @param name
     * @param regexp
     * @return
     */
    public synchronized boolean userLocationMatches(String name, String regexp) {
        String key = "users." + name + ".location";
        String location = attributes.get(key);
        if (location == null) {
            return false;
        } else {
            return Pattern.matches(regexp, location);
        }
    }

    /**
     * 线程安全, 减少锁的持有时间(亦可考虑使用线程安全的Map)
     *
     * @param name
     * @param regexp
     * @return
     */
    public synchronized boolean userLocationMatchesBetter(String name, String regexp) {
        String key = "users." + name + ".location";
        String location;
        synchronized (this) {
            location = attributes.get(key);
        }
        if (location == null) {
            return false;
        } else {
            return Pattern.matches(regexp, location);
        }
    }


}
