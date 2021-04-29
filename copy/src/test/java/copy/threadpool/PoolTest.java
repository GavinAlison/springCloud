package copy.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Before;

import java.util.concurrent.*;

/*
1. 名称池线程
2. 切换名称
3. 明确且安全的关机
 */
public class PoolTest {
    public void test() {
        int cpuSize = Runtime.getRuntime().availableProcessors();
        new ThreadPoolExecutor(cpuSize, cpuSize,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    ExecutorService executorService = null;

    @Before
    public void setup() {
        //名称池线程
        final ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("Orders-%d")
                .setDaemon(true)
                .build();
        executorService = Executors.newFixedThreadPool(10, threadFactory);
    }

    private void process(String messageId) {
        //根据上下文切换名称
        executorService.submit(() -> {
            final Thread currentThread = Thread.currentThread();
            final String oldName = currentThread.getName();
            currentThread.setName("Processing-" + messageId);
            try {
                //real logic here...
            } finally {
                // 内部 try-finally 块当前线程被命名Processing-WHATEVER-MESSAGE-ID-IS
                currentThread.setName(oldName);
            }
        });
    }
}

