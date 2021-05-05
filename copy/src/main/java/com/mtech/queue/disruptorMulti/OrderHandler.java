package com.mtech.queue.disruptorMulti;

import com.lmax.disruptor.EventHandler;

public class OrderHandler implements EventHandler<Order> {

    @Override
    public void onEvent(Order order, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(Thread.currentThread().getName() + " 消费者处理中:" + sequence);
        order.setInfo("info" + order.getId());
        order.setPrice(Math.random());
    }

}