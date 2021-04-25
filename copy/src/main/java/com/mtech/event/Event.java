package com.mtech.event;

import java.util.ArrayList;

// 事件类
public class Event {

    public ArrayList<CallBack> callBacks;

    public void emit() {
        for (CallBack callBack : callBacks) {
            callBack.run();
        }
    }
}

interface CallBack {
    void run();
}

class OnClick implements CallBack {
    @Override
    public void run() {
        System.out.println("this is onclick");
    }
}