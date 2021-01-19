package com.alison;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.connect.data.Struct;

import java.util.Map;

public class DebeziumRecordUtils {
    private static JSONObject jsonObject = new JSONObject();

    public static Map<String, Object> getRecordStructValue(Struct payload, String source) {
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(payload));
        return (Map) jsonObject.get(source);
    }
}
