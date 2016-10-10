package com.yeahmobi.pangolin.impl;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadFactoryImpl implements ThreadFactory {
    private final String name;
    private final AtomicInteger count;

    public ThreadFactoryImpl(String name) {
        this.name = name;
        count = new AtomicInteger(0);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t  = new Thread(r);
        t.setName(name + count.getAndIncrement());
        return t;
    }
}
