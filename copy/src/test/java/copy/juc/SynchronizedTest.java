package copy.juc;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * link==>https://blog.csdn.net/luoweifu/article/details/46613015
 */
public class SynchronizedTest {g
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
}

class SyncThread implements Runnable {
    private static int count;

    public SyncThread() {
        count = 0;
    }

    public void run() {
        synchronized (this) {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getCount() {
        return count;
    }
}
