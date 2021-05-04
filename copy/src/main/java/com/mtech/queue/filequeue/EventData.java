package com.mtech.queue.filequeue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventData {

    private String fileName;

    private String fileContent;

    private long offset;
}
