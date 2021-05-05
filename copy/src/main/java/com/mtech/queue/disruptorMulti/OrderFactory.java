package com.mtech.queue.disruptorMulti;

import com.lmax.disruptor.EventFactory;

public class OrderFactory implements EventFactory {

    @Override
    public Object newInstance() {

        System.out.println("OrderFactory.newInstance");
        return new Order();
    }

}