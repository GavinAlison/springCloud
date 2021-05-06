package com.mtech.queue.disruptorMulti;


import com.lmax.disruptor.WorkHandler;

/**
 * @ClassName DisruptorEventHandler
 * @Describe 消费者  不会重复消费
 */
public class DisruptorEventHandler2 implements WorkHandler<Order> {

    private String id;

    public DisruptorEventHandler2(String id) {
        this.id = id;
    }

    @Override
    public void onEvent(Order event) throws Exception {
        System.out.println("消费者2:" + "id:" + this.id + "  " + event.getInfo());
    }
}