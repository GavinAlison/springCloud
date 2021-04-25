package copy;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExecutorTest {

    @Test
    public void test1() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new MyThread());
        executorService.submit(new MyThread());
        executorService.submit(new MyThread());
    }

}

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("this is time");
    }
}
