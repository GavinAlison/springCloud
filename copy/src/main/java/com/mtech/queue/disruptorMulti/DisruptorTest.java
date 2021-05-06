package com.mtech.queue.disruptorMulti;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @ClassName DisruptorMain
 */
public class DisruptorTest {


    public static void main(String[] args) {
        //最佳线程数目 = （（线程等待时间+线程CPU时间）/线程CPU时间 ）* CPU数目
        //非阻塞型取cpu核数
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(12);
        //构建工厂对象
        EventFactory<Order> disruptorEventFactory = new OrderFactory();
        //ringbuffer大小 是2的N次方
        int ringbuffer = 1024 * 1024;
        //创建event对象 工厂   ringbuffer长度   线程池
        Disruptor<Order> eventDisruptor = new Disruptor<Order>(disruptorEventFactory, ringbuffer, poolExecutor);
        //连接消费者----注册消费者

        //多个消费者不重复吧消费
        DisruptorEventHandler2[] disruptorEventHandlers = new DisruptorEventHandler2[2];
        disruptorEventHandlers[0] = new DisruptorEventHandler2("1");
        disruptorEventHandlers[1] = new DisruptorEventHandler2("2");
        eventDisruptor.handleEventsWithWorkerPool(disruptorEventHandlers);


        //多个消费者还是会产生数据重复
        DisruptorEventHandler[] disruptorEventHandlers2 = new DisruptorEventHandler[2];
        disruptorEventHandlers2[0] = new DisruptorEventHandler("1");
        disruptorEventHandlers2[1] = new DisruptorEventHandler("2");
        eventDisruptor.handleEventsWith(disruptorEventHandlers2);


        //启动
        eventDisruptor.start();
        //创建一个ringbuffer
        RingBuffer<Order> ringBuffer = eventDisruptor.getRingBuffer();
        //创建生产者
        DisruptorEventProducer eventProducer = new DisruptorEventProducer(ringBuffer);
        //分配缓冲区大小为8
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);

        for (int i = 0; i < 100; i++) {
            byteBuffer.putLong(0, i);
            eventProducer.onData(byteBuffer);
        }
        eventDisruptor.shutdown();
    }
}