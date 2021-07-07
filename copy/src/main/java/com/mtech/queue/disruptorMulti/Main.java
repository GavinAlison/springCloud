package com.mtech.queue.disruptorMulti;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        //创建订单工厂
        OrderFactory orderFactory = new OrderFactory();
        //ringbuffer的大小
        int RINGBUFFER_SIZE = 1024;
        //创建disruptor
        Disruptor<Order> disruptor = new Disruptor<Order>(orderFactory, RINGBUFFER_SIZE, Executors.defaultThreadFactory());
        //设置事件处理器 即消费者
        disruptor.handleEventsWith(new OrderHandler());
        disruptor.start();
        RingBuffer<Order> ringBuffer = disruptor.getRingBuffer();
        //-------------生产数据
        for (int i = 0; i < 3; i++) {
            long sequence = ringBuffer.next();
            try {
                Order order = ringBuffer.get(sequence);
                order.setId(i);
            } finally {
                ringBuffer.publish(sequence);
            }
            System.out.println(Thread.currentThread().getName() + " 生产者发布一条数据:" + sequence + " 订单ID：" + i);
        }
        Thread.sleep(1000);
        disruptor.shutdown();
    }

    public void singleHandle() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        RingBuffer<Order> ringBuffer = RingBuffer.create(ProducerType.SINGLE, new OrderFactory(),
                1024, new YieldingWaitStrategy());
        WorkerPool<Order> workerPool = new WorkerPool<Order>(ringBuffer, ringBuffer.newBarrier(),
                new IgnoreExceptionHandler(), new WorkHandler<Order>(){
            @Override
            public void onEvent(Order order) throws Exception {
                System.out.println(order.toString());
            }
        });
        workerPool.start(executor);
        //-------------生产数据
        for (int i = 0; i < 30; i++) {
            long sequence = ringBuffer.next();
            Order order = ringBuffer.get(sequence);
            order.setId(i);
            ringBuffer.publish(sequence);
            System.out.println(Thread.currentThread().getName() + " 生产者发布一条数据:" + sequence + " 订单ID：" + i);
        }
        Thread.sleep(1000);
        workerPool.halt();
        executor.shutdown();

    }

}