package com.blog.utils;

import java.util.concurrent.Callable;

public abstract class CallableAsyc<T> extends ExecutorAsync implements Callable<T> {
    public CallableAsyc() {
    }
}
