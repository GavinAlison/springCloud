package com.mtech.queue.disruptor;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent> {
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println("event value=" + event.get() + " sequence =" + sequence + " endOfBatch=" + endOfBatch);
    }
}