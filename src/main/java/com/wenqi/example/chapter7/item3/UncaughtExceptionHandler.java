package com.wenqi.example.chapter7.item3;

/**
 * 当一个线程由于未捕获的异常而退出时, JVM会把这个时间报告给应用程序提供的UncaughtExceptionHandler
 * 它能检测出某个线程由于未捕获的异常而终结的情况.
 *
 * 如果没有提供任何异常处理器, 那么默认的行为是将栈最终信息输出到System.err
 *
 * @author liangwenqi
 * @date 2021/12/10
 */
public interface UncaughtExceptionHandler {

    void uncaughtException(Thread t, Throwable e);

}
