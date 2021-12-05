package com.wenqi.example.chapter7.item1;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 处理不可中断的阻塞:
 * 1. Socket IO
 * 2. Java.io包中同步IO
 * 3. Selector 异步IO
 * 4. 等待获取某个锁
 *
 * 改写interrupt方法将非标准的取消操作封装在Thread中
 *
 * @author liangwenqi
 * @date 2021/11/25
 */
public class ReaderThread extends Thread {
    private final Socket socket;
    private final InputStream in;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    @Override
    public void interrupt() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            super.interrupt();
        }
    }

    @Override
    public void run() {
        byte[] buf = new byte[1025];
        while (true) {
            try {
                int count = in.read(buf);
                if (count < 0)  {
                    break;
                } else if (count > 0){
                    // processBuffer(buf, count);
                }
            } catch (IOException e) {
                // 允许线程退出
                e.printStackTrace();
            }
        }
    }
}
