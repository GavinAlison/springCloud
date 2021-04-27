//package copy.threadpool;
//
//import org.junit.Test;
//
///**
// * link: https://mp.weixin.qq.com/s/xrudP9H12augcjzFF2kmOA
// * <p>
// * CPU 密集型的程序 - 核心数 + 1
// * n线程，n个CPU，这样才能不让CPU空闲，线程数< CPU核数，会出现CPU抢占线程，然后出现上下文切换
// * 线程数 > CPU核数， 不会出现CPU轮询线程，也会出现上下文切换
// * I/O 密集型的程序 - 核心数 * 2
// *  线程数出现IO等待，CPU会出现空转，之后会调用其他程序，然后就会空闲，一般为了能让CPU满负荷跑，就需要设置线程数=CPU核数*2
// *
// */
//public class ThreadPoolSizeTest {
//    //测试CPU
//    @Test
//    public void CPUUtilizationTest() {
//        for (int n = 0; n < 1; n++) {
//            new Thread(() -> {
//                while (true) {
//                    //每次空循环 1亿 次后，sleep 50ms，模拟 I/O等待、切换
//                    for (int i = 0; i < 100_000_000l; i++) {
//                    }
//                    try {
//                        Thread.sleep(50);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }
//    }
//
//}
