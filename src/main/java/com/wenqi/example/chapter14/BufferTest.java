package com.wenqi.example.chapter14;

/**
 * 不同有界队列的处理方式
 * @author liangwenqi
 * @date 2022/1/10
 */
public class BufferTest {

    public void testGrumpyBoundedBuffer() throws InterruptedException {
        GrumpyBoundedBuffer buffer = new GrumpyBoundedBuffer(10);
        while (true) {
            try {
                Object item = buffer.take();
                // 对于item执行一些操作
                break;
            } catch (Exception e) {
                // 睡眠1秒后重试
                Thread.sleep(1000);
            }
        }
    }

}
