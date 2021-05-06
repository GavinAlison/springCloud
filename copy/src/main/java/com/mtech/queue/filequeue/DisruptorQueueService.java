package com.mtech.queue.filequeue;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 队列数据服务，将数据放在队列中缓存批量写入
 * 队列使用Disruptor
 * 缓存队列在整体系统的进程中不应使用太多，要控制队列使用场景和数量
 */
@Slf4j
public class DisruptorQueueService {

    private int ringBufferSize = 64 * 1024;

    private RingBuffer<EventData> ringBuffer;

    private boolean started;

    private EventDataSaveHandler[] workHandler;

    private Disruptor<EventData> disruptor;

    public DisruptorQueueService(EventDataSaveHandler... workHandler) {
        this.workHandler = workHandler;
    }

    /**
     * 启动线程队列
     */
    public void start() {
        if (!started) {
            //如果有cpu效率问题，更改waitStragety的实现策略
            WaitStrategy waitStrategy = new YieldingWaitStrategy();
            //性能偏低
//            WaitStrategy waitStrategy = new BlockingWaitStrategy();
            ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("event-queue-thread-%d").build();
            EventDataEventFactory eventFactory = new EventDataEventFactory();
            disruptor = new Disruptor<>(eventFactory, ringBufferSize, threadFactory, ProducerType.MULTI, waitStrategy);
            disruptor.handleEventsWithWorkerPool(workHandler);

            disruptor.start();
            log.info("启动事件存储队列");
            ringBuffer = disruptor.getRingBuffer();
            started = true;
        }
    }


    /**
     * 向队列中发送数据
     *
     * @param eventData
     */
    public void publish(EventData eventData) {
        long sequence = ringBuffer.next();
        try {
            EventData queueData = ringBuffer.get(sequence);
            handle(queueData, eventData);
            log.debug("cursor:{}", disruptor.getCursor());
        } finally {
            ringBuffer.publish(sequence);
        }
    }

    private void handle(EventData queueData, EventData eventData) {
        queueData.setFileName(eventData.getFileName());
        queueData.setFileContent(eventData.getFileContent());
        queueData.setOffset(eventData.getOffset());
    }


    public class EventDataEventFactory implements EventFactory<EventData> {
        @Override
        public EventData newInstance() {
            return new EventData();
        }
    }


    public void close() {
        if (disruptor != null) {
            try {
                disruptor.shutdown(3, TimeUnit.SECONDS);
                log.info("save queue close...");
            } catch (TimeoutException e) {
                log.error(e.getMessage(), e);
            }
        }
    }


}
