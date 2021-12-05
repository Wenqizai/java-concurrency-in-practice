package com.wenqi.example.chapter5.cache;

import java.math.BigInteger;

/**
 * @author liangwenqi
 * @date 2021/9/17
 */
public class ExpensiveFunction implements Computable<String, BigInteger> {

    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        // 在经过长时间计算后
        Thread.sleep(10 * 1000);
        return new BigInteger(arg);
    }

}
