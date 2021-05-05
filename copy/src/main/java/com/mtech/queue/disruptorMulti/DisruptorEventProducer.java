package com.mtech.queue.disruptorMulti;
 
import com.lmax.disruptor.RingBuffer;
 
import java.nio.ByteBuffer;
 
/**
 * @ClassName DisruptorEventProducer
 * @Author ywj
 * @Describe  生产者
 * @Date 2020/4/7 9:55
 */
public class DisruptorEventProducer   {
 
 
    public final RingBuffer<Order> ringBuffer;
 
    public DisruptorEventProducer(RingBuffer<Order> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }
 
 
    public void onData(ByteBuffer byteBuffer) {
        // 1.ringBuffer 事件队列 下一个槽
        long sequence = ringBuffer.next();
        Long data = null;
        try {
            //2.取出空的事件队列
            Order disruptorEvent = ringBuffer.get(sequence);
            data = byteBuffer.getLong(0);
            //3.获取事件队列传递的数据
            disruptorEvent.setId(data);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            System.out.println("生产这准备发送数据");
            //4.发布事件
            ringBuffer.publish(sequence);
 
        }
    }
 
 
 
 
 
}