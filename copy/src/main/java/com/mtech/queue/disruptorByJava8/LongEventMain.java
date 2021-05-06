package com.mtech.queue.disruptorByJava8;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.mtech.queue.disruptor.LongEvent;

import java.nio.ByteBuffer;

public class LongEventMain {
    public static void main(String[] args) throws InterruptedException {
        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

        // Connect the handler
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> System.out.println("Event: " + event));
        disruptor.handleEventsWith(LongEventMain::handleEvent);

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.clear();
            bb.putLong(l);
//            bb.putLong(0, l);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.set(buffer.getLong(0)), bb);
            ringBuffer.publishEvent(LongEventMain::translate, bb);
            Thread.sleep(1000);
        }
    }

    public static void handleEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println(event);
    }

    public static void translate(LongEvent event, long sequence, ByteBuffer buffer) {
        event.set(buffer.getLong(0));
    }
}
