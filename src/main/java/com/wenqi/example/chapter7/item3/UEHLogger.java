package com.wenqi.example.chapter7.item3;

import com.wenqi.example.chapter7.item2.LogService;
import com.wenqi.example.chapter7.item2.LogServiceExecutor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 异常捕获器适用实例
 *
 * @author liangwenqi
 * @date 2021/12/10
 */
public class UEHLogger implements UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE,
                "Thread terminated with exception: " + t.getName(),
                e);
    }

    /**
     * 通过注册一个关闭购置来停止日志服务
     */
    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LogService logService;
            try {
                logService = new LogService(new PrintWriter(new File("dd")));
                logService.stop();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }));
    }
}
