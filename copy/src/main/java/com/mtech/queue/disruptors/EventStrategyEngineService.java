package com.mtech.queue.disruptors;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PreDestroy;

/**
 * 事件策略引擎服务
 *
 * @author yxy
 * @date 2019/1/16
 */
@Slf4j
//@EnableConfigurationProperties(EngineConfigProperties.class)
public class EventStrategyEngineService {

    /**
     * 异步事件消息队列服务
     */
    private DisruptorQueueService queueService;

    public void initialize() {
        EventDataSaveHandler[] handlers = new EventDataSaveHandler[30];
        for (int i = 0; i < 30; i++) {
//            handlers[i] = new EventDataSaveHandler(iEventDataService, iStatDataLoader,compositeDataReceiver,engineConfigProperties);
            handlers[i] = new EventDataSaveHandler();
        }
        //事件数据队列
        queueService = new DisruptorQueueService(handlers);
        queueService.start();
    }

    @PreDestroy
    public void close() {
        queueService.close();
    }

}
