package com.mtech.queue.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DisruptorDemo {

    public static void main(String[] args) throws Exception {
        DisruptorDemo disruptorDemo = new DisruptorDemo();
        EventFactory<LongEvent> eventFactory = new LongEventFactory();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        int ringBufferSize = 1024 * 1024; // RingBuffer 大小，必须是 2 的 N 次方；

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(eventFactory,
                ringBufferSize, executor, ProducerType.SINGLE,
                new YieldingWaitStrategy());

        EventHandler<LongEvent> eventHandler = new LongEventHandler();
        disruptor.handleEventsWith(eventHandler);

        disruptor.start();
        int k = 0;
        while (k++ < 20) {
            disruptorDemo.sendMsg(disruptor);
        }
        TimeUnit.SECONDS.sleep(10);
        disruptor.shutdown();
        executor.shutdown();
    }

    public void sendMsg(Disruptor disruptor) {
        // 发布事件；
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        long sequence = ringBuffer.next();//请求下一个事件序号；
        try {
            LongEvent event = ringBuffer.get(sequence);//获取该序号对应的事件对象；
            long data = getEventData();//获取要通过事件传递的业务数据；
            event.set(data);
        } finally {
            ringBuffer.publish(sequence);//发布事件；
        }
    }

    private long getEventData() {
        return 1L;
    }
}





