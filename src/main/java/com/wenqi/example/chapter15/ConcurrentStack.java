package com.wenqi.example.chapter15;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用Treiber算法构造的非阻塞的栈
 *
 * @author liangwenqi
 * @date 2022/1/20
 */
@ThreadSafe
public class ConcurrentStack<E> {
    AtomicReference<Node<E>> top = new AtomicReference<>();

    public void push(E item) {
        Node<E> newHead = new Node<>(item);
        Node<E> oldHead;
        do {
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead));
    }

    public E pop() {
        Node<E> newHead;
        Node<E> oldHead;
        do {
            oldHead = top.get();
            if (oldHead == null) {
                return null;
            }
            // 多线程执行该步, 会修改到结构, 所以不能写: oldHead.next = null;
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }

    private static class Node<E> {
        public final E item;
        public Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }
}
