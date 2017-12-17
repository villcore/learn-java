package com.villcore.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadPoolSubmit {
    public static void main(String[] args) {
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10,
                600,
                300L,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                new ThreadFactory() {
                    private AtomicLong threadNo = new AtomicLong();

                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setName("thread-" + threadNo.addAndGet(1));
                        return t;
                    }
                }, rejectedExecutionHandler);

        for(int i = 0; i < 100; i++) {
            threadPoolExecutor.submit(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.printf("%s running ...\n", Thread.currentThread().getName());
                }
            });
        }
    }
}
