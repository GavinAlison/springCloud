package copy.threadpool;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.*;

public class ThreadPoolTest {

    /**
     * 创建一个线程池，线程池大小固定为10，阻塞队列大小为10，最大线程池为20，拒绝策略为默认AbortPolicy。
     * 分页处理，有100页任务需要处理，需要处理100次
     */
    public void fixTest() {
        ExecutorService executorService = new ThreadPoolExecutor(10, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10));

        //假如要分页处理100页数据
        int totalPage = 100;

        //一共10个线程
        int totalThreadNum = 10;

        //直接开10个固定线程去处理任务
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                /**
                 *   线程1，查询，处理1-10页
                 *   线程2，查询，处理11-20页
                 *   线程3，查询，处理21-30页
                 *   ....
                 */
            });
        }
    }

    //如果我们的场景是需要要将100页数据从主线程提交到线程池中处理，而不是上述在线程池中直接查询100页数据进行处理，该怎么办呢？
    public void test1() {
        ExecutorService executorService = new ThreadPoolExecutor(10, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10));

        int totalPage = 100;
        int totalThreadNum = 10;

        //直接把100页提交到线程池
        executorService.submit(() -> {
            try {
                //任务需要处理1秒钟
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println(a);
        });
    }


    @Test
    public void test2() {
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

        int totalPage = 100;
        int totalThreadNum = 10;
        //直接把100页提交到线程池
        for (int i = 0; i < totalPage; i++) {
            final int a = i;
            executorService.submit(() -> {
                try {
                    //任务需要处理3秒钟
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println(LocalDateTime.now(ZoneId.systemDefault()) + "=====>" + a);
        }
        System.out.println("========1");
    }


}

