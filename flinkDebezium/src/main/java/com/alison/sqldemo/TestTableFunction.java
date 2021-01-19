package com.alison.sqldemo;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.calcite.shaded.com.google.common.base.Strings;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;

public class TestTableFunction extends TableFunction {

    private String separator = ",";

    public TestTableFunction(String separator) {
        this.separator = separator;
    }

    //和传入数据进行计算的逻辑，参数个数任意
    public void eval(String input) {

        Row row = null;

        if (Strings.isNullOrEmpty(input)) {

            row = new Row(2);
            row.setField(0, null);
            row.setField(1, 0);
            collect(row);

        } else {

            String[] split = input.split(separator);

            for (String word : split) {
                row = new Row(2);
                row.setField(0, word);
                row.setField(1, word.length());
                collect(row);
            }

        }

    }

    @Override
    public TypeInformation getResultType() {
        return Types.ROW(Types.STRING, Types.INT);
    }
}
