package com.wenqi.example.chapter11;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

/**
 * 锁分解
 * @author liangwenqi
 * @date 2021/12/29
 */
@ThreadSafe
public class ServerStatus {
    @GuardedBy("this") public final Set<String> users;
    @GuardedBy("this") public final Set<String> queries;

    public ServerStatus() {
        users = new HashSet<>();
        queries = new HashSet<>();
    }

    // ===================================== 大粒度的锁 =====================================//
    public synchronized void addUser(String user) {
        users.add(user);
    }
    public synchronized void addQuery(String query) {
        queries.add(query);
    }
    public synchronized void removeUser(String user) {
        users.remove(user);
    }
    public synchronized void removeQuery(String query) {
        queries.remove(query);
    }

    // ===================================== 分解大粒度的锁 =====================================//
    public void addUser2(String user) {
        synchronized (users) {
            users.add(user);
        }
    }
    public void addQuery2(String query) {
        synchronized (queries) {
            queries.add(query);
        }
    }
    public void removeUser2(String user) {
        synchronized (users) {
            users.remove(user);
        }
    }
    public void removeQuery2(String query) {
        synchronized (queries) {
            queries.remove(query);
        }
    }
}
