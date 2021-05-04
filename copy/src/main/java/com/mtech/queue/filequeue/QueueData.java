package com.mtech.queue.filequeue;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueueData {
    public static final long MAX_SIZE = 32 * 1024 * 1024;
    public static final long TIMEOUT = 5L;
    /**
     * 队列数据
     */
    private EventData eventData;

}
