package com.wenqi.example.chapter13;

import com.wenqi.example.chapter10.Account;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 避免死锁
 * 轮询, 定时锁, 避免获取锁顺序的不一致
 * 如果获取到一把锁, 获取另一把锁时超时, 则退出释放锁并重新获取锁
 *
 * @author liangwenqi
 * @date 2022/1/10
 */
public class CycleLock {
    private Random random = new Random();

    public boolean transferMoney(Account fromAccount, Account toAccount, BigDecimal amount, long timeout, TimeUnit unit) throws InterruptedException {
        long fixedDelay = getFixedDelayComponentNanos(timeout, unit);
        long randMod = getRandomDelayModulusNanos(timeout, unit);
        long stopTime = System.nanoTime() + unit.toNanos(timeout);

        while (true) {
            if (fromAccount.lock.tryLock()) {
                try {
                    if (toAccount.lock.tryLock()) {
                        try {
                            if (fromAccount.getBalance().compareTo(amount) < 0) {
                                throw new RuntimeException("余额不足");
                            } else {
                                fromAccount.debit(amount);
                                toAccount.credit(amount);
                                return true;
                            }
                        } finally {
                            toAccount.lock.unlock();
                        }
                    }
                } finally {
                    fromAccount.lock.unlock();
                }
            }

            // 指定时间内未获取到锁, 退出
            if (System.nanoTime() < stopTime) {
                return false;
            }

            // 睡眠指定时间 + 随机数, 避免活锁
            Thread.sleep((fixedDelay + random.nextLong() % randMod) / 1000);
        }
    }

    private long getRandomDelayModulusNanos(long timeout, TimeUnit unit) {
        return 3L;
    }

    private long getFixedDelayComponentNanos(long timeout, TimeUnit unit) {
        return unit.toNanos(timeout);
    }
}
