package com.wenqi.example.chapter10;

import java.math.BigDecimal;

/**
 *
 * @author liangwenqi
 * @date 2021/12/28
 */
public class SolveLeftRightDeadlock {
    private static final Object tieLock = new Object();

    public void transferMoney(final Account fromAccount, final Account toAccount, final BigDecimal amount) {
        class Helper {
            public void transfer() {
                if (fromAccount.getBalance().compareTo(amount) < 0) {
                    throw new RuntimeException("余额不足");
                } else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                }
            }
        }

        int fromHash = System.identityHashCode(fromAccount);
        int toHash = System.identityHashCode(toAccount);

        if (fromHash < toHash) {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    new Helper().transfer();
                }
            }
        } else {
            synchronized (tieLock) {
                synchronized (fromAccount) {
                    synchronized (toAccount) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }
}
