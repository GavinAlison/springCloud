package com.mtech.thread;

import java.util.concurrent.*;


public class ThreadPoolUtil {

    public void createThreadPool(){
        ExecutorService executorService = new ThreadPoolExecutor(10, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10), new RejectedExecutionHandler() {
            //变更点，注意，此处把线程池中的阻塞队列拿出来，重新put Runnable
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    executor.getQueue().put(r);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.submit(()-> {});
    }
}
