package com.wenqi.example.chapter6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 多线程版本
 *
 * 缺点: 无限创建线程带来的问题: 1. 线程的开销(创建和销毁) 2. 资源消耗(空闲线程占用内存和给GC带来压力)
 * @author liangwenqi
 * @date 2021/9/24
 */
public class ThreadPerTaskWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        while (true) {
            final Socket connection = serverSocket.accept();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    handleRequest(connection);
                }
            };
            new Thread(task).start();
        }
    }

    public static void handleRequest(Socket socket) {

    }
}
