package com.alison.sqldemo;

import org.apache.flink.table.functions.AggregateFunction;

import java.util.Iterator;

public class TestAggregateFunction extends AggregateFunction<Long, TestAggregateFunction.SumAll> {
    //返回最终结果
    @Override
    public Long getValue(SumAll acc) {
        return acc.sum;
    }

    //构建保存中间结果的对象
    @Override
    public SumAll createAccumulator() {
        return new SumAll();
    }

    //和传入数据进行计算的逻辑
    public void accumulate(SumAll acc, long iValue) {
        acc.sum += iValue;
    }

    //减去要撤回的值
    public void retract(SumAll acc, long iValue) {
        acc.sum -= iValue;
    }

    //从每个分区把数据取出来然后合并
    public void merge(SumAll acc, Iterable<SumAll> it) {

        Iterator<SumAll> iter = it.iterator();

        while (iter.hasNext()) {
            SumAll a = iter.next();
            acc.sum += a.sum;

        }
    }

    //重置内存中值时调用
    public void resetAccumulator(SumAll acc) {
        acc.sum = 0L;
    }

    public static class SumAll {
        public long sum = 0;
    }

}
