package com.wenqi.example.chapter6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 单线程处理任务, 线程安全, IO阻塞, CPU利用率低, 吞吐量低
 *
 * @author liangwenqi
 * @date 2021/9/24
 */
public class SingleThreadWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket();
        while (true) {
            Socket connection = socket.accept();
            handleRequest(connection);
        }
    }

    public static void handleRequest(Socket socket) {

    }
}
