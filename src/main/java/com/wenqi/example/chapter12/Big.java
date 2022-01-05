package com.wenqi.example.chapter12;

/**
 * 测试资源泄露
 * @author liangwenqi
 * @date 2021/12/31
 */
public class Big {
    double[] data = new double[100000];

    void testLeak() {

    }
}
