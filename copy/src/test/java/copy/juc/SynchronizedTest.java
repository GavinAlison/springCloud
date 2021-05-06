package copy.juc;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * link==>https://blog.csdn.net/luoweifu/article/details/46613015
 */
public class SynchronizedTest {
    @Test
    public void test() throws Exception {
        SyncThread syncThread = new SyncThread();
        /**
         * 锁定的是同一个对象，线程互斥的，只有对象上的线程执行完成并释放锁，下个线程才可以获取对象锁，并执行
         */
//        Thread thread1 = new Thread(syncThread, "SyncThread1");
//        Thread thread2 = new Thread(syncThread, "SyncThread1");
        /**
         *  synchronized锁定的是对象， 锁了各自的对象
         */
        Thread thread1 = new Thread(new SyncThread(), "SyncThread1");
        Thread thread2 = new Thread(new SyncThread(), "SyncThread2");
        thread1.start();
        thread2.start();
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void test1() throws Exception {
        Counter counter = new Counter();
        Thread thread1 = new Thread(counter, "A");
        Thread thread2 = new Thread(counter, "B");
        thread1.start();
        thread2.start();
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void test2() throws Exception {
        Account account = new Account("zhang san", 10000.0f);
        AccountOperator accountOperator = new AccountOperator(account);

        final int THREAD_NUM = 5;
        Thread threads[] = new Thread[THREAD_NUM];
        for (int i = 0; i < THREAD_NUM; i++) {
            threads[i] = new Thread(accountOperator, "Thread" + i);
            threads[i].start();
        }
        TimeUnit.SECONDS.sleep(2);
    }

    interface InnerClass {
        void getXX();
    }


}

