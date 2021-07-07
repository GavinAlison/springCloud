package com.mtech.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadUtil {
    private ThreadUtil() {
    }

    public static ThreadFactory createThreadFactory(final String pattern, final boolean daemon) {
        return new ThreadFactory() {
            private final AtomicLong threadEpoch = new AtomicLong(0L);

            @Override
            public Thread newThread(Runnable r) {
                String threadName = pattern;
                if (pattern.contains("%d")) {
                    threadName = String.format(pattern, threadEpoch.addAndGet(1L));
                }
                Thread thread = new Thread(r, threadName);
                thread.setDaemon(daemon);
                return thread;
            }
        };
    }
}
