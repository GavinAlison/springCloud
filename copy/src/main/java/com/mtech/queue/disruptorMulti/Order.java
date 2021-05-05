package com.mtech.queue.disruptorMulti;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

    //订单ID
    private long id;

    //订单信息
    private String info;

    //订单价格
    private double price;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", info='" + info + '\'' +
                ", price=" + price +
                '}';
    }
}