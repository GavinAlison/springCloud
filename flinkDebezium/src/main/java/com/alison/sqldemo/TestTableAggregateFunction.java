package com.alison.sqldemo;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.table.functions.TableAggregateFunction;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;

public class TestTableAggregateFunction extends TableAggregateFunction<Row, TestTableAggregateFunction.Top2> {
    //创建保留中间结果的对象
    @Override
    public Top2 createAccumulator() {
        Top2 t = new Top2();
        t.f1 = Integer.MIN_VALUE;
        t.f2 = Integer.MIN_VALUE;

        return t;
    }

    //与传入值进行计算的方法
    public void accumulate(Top2 t, Integer v) {
        //如果传入的值比内存中第一个值大，那就用第一个值替换第二个值，传入的值替换第一个值；
        //如果传入的值比第二个值大比第一个小，那么就替换第二个值。
        if (v > t.f1) {
            t.f2 = t.f1;
            t.f1 = v;
        } else if (v > t.f2) {
            t.f2 = v;
        }
    }

    //合并分区的值
    public void merge(Top2 t, Iterable<Top2> iterable) {
        for (Top2 otherT : iterable) {
            accumulate(t, otherT.f1);
            accumulate(t, otherT.f2);
        }
    }

    //拿到返回结果的方法
    public void emitValue(Top2 t, Collector<Row> out) {
        Row row = null;
        //发射数据
        //如果第一个值不是最小的int值，那就发出去
        //如果第二个值不是最小的int值，那就发出去
        if (t.f1 != Integer.MIN_VALUE) {
            row = new Row(2);
            row.setField(0, t.f1);
            row.setField(1, 1);
            out.collect(row);
        }
        if (t.f2 != Integer.MIN_VALUE) {
            row = new Row(2);
            row.setField(0, t.f2);
            row.setField(1, 2);
            out.collect(row);
        }
    }

    //撤回流拿结果的方法，会发射撤回数据
    public void emitUpdateWithRetract(Top2 t, RetractableCollector<Row> out) {
        Row row = null;
        //如果新旧值不相等，才需要撤回，不然没必要
        //如果旧值不等于int最小值，说明之前发射过数据，需要撤回
        //然后将新值发射出去
        if (!t.f1.equals(t.oldF1)) {
            if (t.oldF1 != Integer.MIN_VALUE) {
                row = new Row(2);
                row.setField(0, t.oldF1);
                row.setField(1, 1);
                out.retract(row);
            }
            row = new Row(2);
            row.setField(0, t.f1);
            row.setField(1, 1);
            out.collect(row);
            t.oldF1 = t.f1;
        }
        //和上面逻辑一样，只是一个发射f1，一个f2
        if (!t.f2.equals(t.oldF2)) {
            // if there is an update, retract old value then emit new value.
            if (t.oldF2 != Integer.MIN_VALUE) {
                row = new Row(2);
                row.setField(0, t.oldF2);
                row.setField(1, 2);
                out.retract(row);
            }
            row = new Row(2);
            row.setField(0, t.f2);
            row.setField(1, 2);
            out.collect(row);
            t.oldF2 = t.f2;
        }
    }

    //保留中间结果的类
    public class Top2 {
        public Integer f1;
        public Integer f2;
        public Integer oldF1;
        public Integer oldF2;

    }

    @Override
    public TypeInformation<Row> getResultType() {
        return Types.ROW(Types.INT, Types.INT);
    }
}
