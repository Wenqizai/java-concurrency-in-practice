package com.wenqi.example.chapter10.item1;

import java.math.BigDecimal;

/**
 * 顺序死锁
 * @author liangwenqi
 * @date 2021/12/27
 */
public class LeftRightDeadlock {
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight() {
        synchronized (left) {
            synchronized (right) {
                // doSomething()
            }
        }
    }

    public void rightLeft() {
        synchronized (right) {
            synchronized (left) {
                // doSomething()
            }
        }
    }

    /**
     * 容易发生顺序死锁
     * @param fromAccount
     * @param toAccount
     * @param amount
     */
    public void transferMoney(Account fromAccount, Account toAccount, BigDecimal amount) {
        synchronized (fromAccount) {
            synchronized (toAccount) {
                if (fromAccount.getBalance().compareTo(amount) < 0) {
                    throw new RuntimeException("余额不足");
                } else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                }
            }
        }
    }

}
