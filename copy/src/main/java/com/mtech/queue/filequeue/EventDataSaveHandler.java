package com.mtech.queue.filequeue;

import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 事件队列数据处理消费端handler
 */
@Slf4j
public class EventDataSaveHandler implements WorkHandler<EventData> {

    private AtomicLong counter = new AtomicLong();
    private AtomicLong errorCounter = new AtomicLong();

    @Override
    public void onEvent(EventData eventData) {
        try {
            //队列数据取出时，存储租户号
            counter.incrementAndGet();
            execute(eventData);
        } catch (Exception e) {
            errorCounter.incrementAndGet();
            //doSomething()
//            log.error("历史数据处理异常数量:{}, save event:{} ", errorCounter.get(), queueData.getEventData().getEventObject(), e);
        } finally {
            eventData = null;
        }
    }

    private void execute(EventData eventData) throws Exception {
        FileUtils.write(new File(eventData.getFileName()), eventData.getFileContent(), StandardCharsets.UTF_8, true);
    }


}
