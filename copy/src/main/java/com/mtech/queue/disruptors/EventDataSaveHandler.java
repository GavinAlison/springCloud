package com.mtech.queue.disruptors;

import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 事件队列数据处理消费端handler
 *
 * @author yxy
 * @date 2018/12/12
 */
@Slf4j
public class EventDataSaveHandler implements WorkHandler<QueueData> {

    private AtomicLong counter = new AtomicLong();
    private AtomicLong errorCounter = new AtomicLong();

    @Override
    public void onEvent(QueueData queueData) {
        try {
            EventData eventData = queueData.getEventData();
            //队列数据取出时，存储租户号
            counter.incrementAndGet();
        } catch (Exception e) {
            errorCounter.incrementAndGet();
            //doSomething()
//            log.error("历史数据处理异常数量:{}, save event:{} ", errorCounter.get(), queueData.getEventData().getEventObject(), e);
        } finally {
            queueData.setEventData(null);
        }
    }


}
