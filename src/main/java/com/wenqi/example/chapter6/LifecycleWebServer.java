package com.wenqi.example.chapter6;

import javafx.scene.paint.Stop;
import sun.misc.Request;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * 支持关闭操作的web服务器
 *
 * @author liangwenqi
 * @date 2021/9/24
 */
public class LifecycleWebServer {
    private final ExecutorService exec = Executors.newFixedThreadPool(100);

    public void start() throws IOException {
        try (ServerSocket socket = new ServerSocket(80)) {
            while (!exec.isShutdown()) {
                final Socket connection = socket.accept();
                try {
                    exec.execute(new Runnable() {
                        @Override
                        public void run() {
                            handleRequest(connection);
                        }
                    });
                } catch (RejectedExecutionException e) {
                    if (!exec.isShutdown()) {
                        doLog("task submission rejected", e);
                    }
                }
            }
        }
    }

    public void stop() {
        exec.shutdown();
    }

    public void handleRequest(Socket connection) {
        Request request = readRequest(connection);
        if (Boolean.TRUE.equals(isShutdownRequest(request))) {
            stop();
        } else {
            dispatcherRequest(request);
        }
    }

    private Request readRequest(Socket connection){
        return new Request() {
            @Override
            public void execute() {

            }
        };
    }

    private Boolean isShutdownRequest(Request request) {
        return Boolean.TRUE;
    }

    private void dispatcherRequest(Request request) {

    }

    private static void doLog(String message, Exception exception){}
}
