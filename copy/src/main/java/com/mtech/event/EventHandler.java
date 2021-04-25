package com.mtech.event;


import lombok.Getter;

import java.lang.reflect.Method;

public class EventHandler {

    private Object sender;

    private String callback;

    private EventArgs eventArgs;

    public EventHandler(Object sender, String callback) {
        this.sender = sender;
        this.callback = callback;
    }

    public void emit() {
        Class<?> sendType = this.sender.getClass();
        try {
            Method method = sendType.getMethod(this.callback, eventArgs.getPositionx().getClass(), eventArgs.getPositiony().getClass());
            method.invoke(this.sender, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        Button button = new Button();
//        EventHandler obclick = new EventHandler(button, "obclick");
//        obclick.emit();
//    }
}

class Button {
    private String x;
    private String y;

    public void onClick(String x, String y) {
        this.x = x;
        this.y = y;
        System.out.println("position x: " + x + ", y:" + y);
    }
}

@Getter
class EventArgs {
    private String positionx;
    private String positiony;
}