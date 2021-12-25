package com.wenqi.example.chapter8.item1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 单线程池引发线程饥饿死锁
 * @author liangwenqi
 * @date 2021/12/10
 */
public class ThreadDeadlock {
    ExecutorService exec = Executors.newSingleThreadExecutor();

    public class RenderPageTask implements Callable<String> {
        @Override
        public String call() throws ExecutionException, InterruptedException {
            Future<String> header, footer;
            header = exec.submit(new LoaderFileTask("header.html"));
            footer = exec.submit(new LoaderFileTask("footer.html"));
            String page = renderBody();
            // 将发生死锁 -> 由于任务在等待子任务的结果
            return header.get() + page + footer.get();
        }

        public String renderBody() {
            return null;
        }
    }

    class LoaderFileTask implements Callable<String>{

        private String file;

        public LoaderFileTask(String file) {
            this.file = file;
        }

        @Override
        public String call() throws Exception {
            return null;
        }
    }
}
