package com.mtech.queue.filequeue;

import com.google.common.collect.Maps;

import java.util.Map;

public class Producer {

    private final static String[] optional = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "14", "25", "26", "27", "28", "29",
    };
    private Map<String, QueueData> queueDataMap = Maps.newConcurrentMap();

    public void initialize() {
        for (String foo : optional) {
            queueDataMap.put(foo, new QueueData());
        }
    }

    public void sendMsg(String fileName, String content, long time) {
        QueueData queueData = queueDataMap.get(fileName);
        EventData eventData = queueData.getEventData();
        if (eventData != null) {
            StringBuffer append = eventData.getFileContent().append("\n").append(content);
            if (Long.valueOf(append.toString()) > queueData.MAX_SIZE) {
                // sendMsg
            } else if (time >= queueData.TIMEOUT) {
                // sendMsg
            } else {
                // sleep
            }
        }

    }
}
