package com.mtech.queue.disruptorMulti;


import com.lmax.disruptor.EventHandler;

/**
 * @ClassName DisruptorEventHandler
 * @Describe 消费者
 */
public class DisruptorEventHandler implements EventHandler<Order> {

    private String id;

    public DisruptorEventHandler(String id) {
        this.id = id;
    }

    @Override
    public void onEvent(Order event, long l, boolean b) throws Exception {
        System.out.println("消费者:" + "id:" + this.id + "  " + event.getInfo());
    }
}