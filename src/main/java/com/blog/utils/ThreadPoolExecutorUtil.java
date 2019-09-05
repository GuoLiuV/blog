package com.blog.utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ThreadPoolExecutorUtil {
    private static ExecutorService executor;
    private static AtomicInteger mThreadNum = new AtomicInteger(1);
    @Value("${glv.thread.core-pool-size:50}")
    private int corePoolSize;
    @Value("${glv.thread.maximum-pool-size:100}")
    private int maximumPoolSize;
    @Value("${glv.thread.keep-alive-time:60}")
    private int keepAliveTime;
    @Value("${glv.thread.blocking-queue:100}")
    private int blockingQueue;

    public ThreadPoolExecutorUtil() {
    }

    @PostConstruct
    public void init() {
        executor = new ExecutorThreadPoolExecutor(this.corePoolSize, this.maximumPoolSize, (long)this.keepAliveTime, TimeUnit.MINUTES, new PriorityBlockingQueue(this.blockingQueue), (r) -> {
            return new Thread(r, "ExecutorUtil-thread-" + mThreadNum.getAndIncrement());
        });
        ((ThreadPoolExecutor)executor).allowCoreThreadTimeOut(true);
    }

    public static Future submit(CallableAsyc task) throws InterruptedException, ExecutionException {
        return executor.submit(task);
    }

    public static void submit(RunnableAsyc task) throws InterruptedException, ExecutionException {
        executor.submit(task);
    }

    public void shutdown() {
        try {
            if (executor != null) {
                executor.shutdown();
                boolean loop = true;

                do {
                    loop = !executor.awaitTermination(60L, TimeUnit.SECONDS);
                } while(loop);

                UnitedLogger.debug("------------ 线程结束 ----------\n");
            }
        } catch (InterruptedException var2) {
            UnitedLogger.error("------------ 线程异常 [InterruptedException] ----------\n");
            UnitedLogger.error(var2);
        }

    }

    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return this.maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public int getKeepAliveTime() {
        return this.keepAliveTime;
    }

    public void setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public int getBlockingQueue() {
        return this.blockingQueue;
    }

    public void setBlockingQueue(int blockingQueue) {
        this.blockingQueue = blockingQueue;
    }
}
