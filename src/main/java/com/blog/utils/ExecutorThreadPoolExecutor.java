package com.blog.utils;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorThreadPoolExecutor extends ThreadPoolExecutor {
    public ExecutorThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new ExecutorThreadPoolExecutor.SFutureTask(runnable, value, this.getSort(runnable));
    }

    private int getSort(Object o) {
        int sort = 0;
        if (o instanceof ExecutorAsync) {
            sort = ((ExecutorAsync)o).getSort();
        }

        return sort;
    }

    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new ExecutorThreadPoolExecutor.SFutureTask(callable, this.getSort(callable));
    }

    private class SFutureTask<T> extends FutureTask<T> implements Comparator<SFutureTask>, Comparable<ExecutorThreadPoolExecutor.SFutureTask> {
        private int sort = 0;

        public SFutureTask(Callable<T> callable, int sort) {
            super(callable);
            this.sort = sort;
        }

        public SFutureTask(Runnable runnable, T result, int sort) {
            super(runnable, result);
            this.sort = sort;
        }

        public int compare(ExecutorThreadPoolExecutor.SFutureTask o1, ExecutorThreadPoolExecutor.SFutureTask o2) {
            if (o1.sort > o2.sort) {
                return 1;
            } else {
                return o1.sort < o2.sort ? -1 : 0;
            }
        }

        public int compareTo(ExecutorThreadPoolExecutor.SFutureTask o) {
            return o.sort;
        }
    }
}
