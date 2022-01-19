package com.wenqi.example.chapter14;

import net.jcip.annotations.ThreadSafe;

/**
 * 有界缓存处理前提条件不满足的情况一
 * <p>
 * 1. 当前提条件不满足时, 直接抛出异常给调用者, 不执行后续逻辑 (先检查, 后运行)
 *
 * @author liangwenqi
 * @date 2022/1/10
 */
@ThreadSafe
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    public GrumpyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) {
        if (isFull()) {
            throw new RuntimeException("队列已满");
        }
        doPut(v);
    }

    public synchronized V take() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        return doTake();
    }
}
