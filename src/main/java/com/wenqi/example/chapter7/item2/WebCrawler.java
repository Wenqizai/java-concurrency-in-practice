package com.wenqi.example.chapter7.item2;

import net.jcip.annotations.GuardedBy;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 任务关闭时, 保存那些尚未完成的任务
 * @author liangwenqi
 * @date 2021/12/3
 */
public abstract class WebCrawler {
    private volatile TrackingExecutor exec;

    @GuardedBy("this")
    private final Set<URL> urlsToCrawl = new HashSet<>();

    public synchronized void start() {
        exec = new TrackingExecutor(Executors.newCachedThreadPool());
        for (URL url : urlsToCrawl) {
            submitCrawlTask(url);
        }
        urlsToCrawl.clear();
    }

    public synchronized void stop(Long timeOut, TimeUnit timeUnit) throws InterruptedException {
        try {
            saveUnCrawled(exec.shutdownNow());
            if (exec.awaitTermination(timeOut, timeUnit)) {
                saveUnCrawled(exec.getCancelledTasks());
            }
        } finally {
            exec = null;
        }
    }

    protected abstract List<URL> processPage(URL url);

    private void saveUnCrawled(List<Runnable> unCrawled) {
        for (Runnable task : unCrawled) {
            urlsToCrawl.add(((CrawlTask)task).getPage());
        }
    }

    private void submitCrawlTask(URL link) {
        exec.execute(new CrawlTask(link));
    }

    private class CrawlTask implements Runnable {
        private final URL url;

        public CrawlTask(URL url) {
            this.url = url;
        }

        @Override
        public void run() {
            for (URL link : processPage(url)) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                submitCrawlTask(link);
            }
        }

        public URL getPage() {
            return url;
        }
    }
}
