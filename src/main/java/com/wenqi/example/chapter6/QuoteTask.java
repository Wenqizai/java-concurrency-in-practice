package com.wenqi.example.chapter6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 在预定时间内请求旅游报价
 * 1. 只显示在指定时间内收到的信息, 对于没有及时响应的服务, 显示默认值
 * 2. 使用invokeAll来接收所有的服务响应
 * 3. invokeAll返回的结果: 要么每个任务正常完成, 要么被取消
 * 4. 客户端可以通过get或isCancelled来判断究竟是何种情况
 *
 * @author liangwenqi
 * @date 2021/9/24
 */
public class QuoteTask implements Callable<QuoteTask.TravelQuote> {
    private final TravelCompany company;
    private final TravelInfo travelInfo;
    private final ExecutorService exec = Executors.newFixedThreadPool(100);

    public QuoteTask(TravelCompany company, TravelInfo travelInfo) {
        this.company = company;
        this.travelInfo = travelInfo;
    }

    @Override
    public TravelQuote call() throws Exception {
        return company.solicitQuote(travelInfo);
    }

    public List<TravelQuote> getRankedTravelQuotes(TravelInfo travelInfo,
                                                   Set<TravelCompany> companies,
                                                   Comparator<TravelQuote> ranking,
                                                   long time,
                                                   TimeUnit unit
    ) throws InterruptedException {
        List<QuoteTask> tasks = new ArrayList<>();
        for (TravelCompany company : companies) {
            tasks.add(new QuoteTask(company, travelInfo));
        }
        List<Future<TravelQuote>> futures = exec.invokeAll(tasks, time, unit);

        List<TravelQuote> quotes = new ArrayList<>(tasks.size());
        Iterator<QuoteTask> taskIterator = tasks.iterator();
        for (Future<TravelQuote> future : futures) {
            QuoteTask task = taskIterator.next();
            try {
                quotes.add(future.get());
            } catch (ExecutionException e) {
                quotes.add(task.getFailureQuote(e.getCause()));
            } catch (CancellationException e) {
                quotes.add(task.getTimeoutQuote(e.getCause()));
            }
        }
        quotes.sort(ranking);
        return quotes;
    }

    private TravelQuote getFailureQuote(Throwable e) {
        return new TravelQuote();
    }

    private TravelQuote getTimeoutQuote(Throwable e) {
        return new TravelQuote();
    }

    class TravelQuote {
    }

    class TravelCompany {
        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        public TravelQuote solicitQuote(TravelInfo travelInfo) {
            return new TravelQuote();
        }
    }

    class TravelInfo {}
}
