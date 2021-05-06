package com.mtech.queue.disruptors;

public class DisruptorDemo {
    public static void main(String[] args) {
        DisruptorQueueService disruptorQueueService = new DisruptorQueueService();
        disruptorQueueService.start();
        EventData eventData = new EventData();
        disruptorQueueService.publish(eventData);
    }
}
