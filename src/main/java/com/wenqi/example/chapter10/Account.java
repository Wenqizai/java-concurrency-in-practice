package com.wenqi.example.chapter10;

import sun.reflect.generics.tree.VoidDescriptor;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liangwenqi
 * @date 2021/12/28
 */
public class Account {
    private BigDecimal balance = new BigDecimal("1000000");
    public Lock lock = new ReentrantLock(false);

    public void debit(BigDecimal amount) {}

    public void credit(BigDecimal amount) {}

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
