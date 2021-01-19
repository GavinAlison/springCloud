package com.alison;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

/**
 * @Author alison
 * @Date 2020/4/12
 * @Version 1.0
 * @Description
 */
@Slf4j
public class KafkaProducerTest {

    private static KafkaProducer<String, String> producer;
    private final static String TOPIC = "topic";

    @Before
    public void before() {
//        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) StaticLoggerBinder.getSingleton().getLoggerFactory().getLogger("org.apache.kafka");
//        logger.setLevel(Level.OFF);

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ACKS_CONFIG, "all");
        props.put(RETRIES_CONFIG, 3); // 重试次数
        props.put(BATCH_SIZE_CONFIG, 1000); // 批量发送大小
        props.put(BUFFER_MEMORY_CONFIG, 33554432); // 缓存大小，根据本机内存大小配置
        props.put(LINGER_MS_CONFIG, 1000); // 发送频率，满足任务一个条件发送
        props.put(CLIENT_ID_CONFIG, "producer-syn-2"); // 发送端id,便于统计
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(TRANSACTIONAL_ID_CONFIG, "producer-1"); // 每台机器唯一
        props.put(ENABLE_IDEMPOTENCE_CONFIG, true); // 设置幂等性

        producer = new KafkaProducer<String, String>(props);
        producer.initTransactions();

    }


    /****
     *
     * 测试生产者
     *
     *  *
     *  * offset = 42, key = {"database":"demo","table":"test","pk.id":4},
     *  value = {"database":"demo","table":"test","type":"insert","ts":1578016448,"xid":63708,
     *  "commit":true,"data":{"id":4,"name":"吕洞宾"}}
     *  * @author yxy
     *  * @date 2018/12/11
     *  */

    @Test
    public void testInsert() throws Exception {

        for (int i = 0; i < 1; i++) {
            JSONObject object = new JSONObject();

            object.put("id", "1");
            object.put("name", "张三");
            QueueData<JSONObject> queueData = new QueueData();
            queueData.setDatabase("demo");
            queueData.setTable("test");
            queueData.setType("DMLOperateType.INSERT");
            queueData.setTs(System.currentTimeMillis());
            queueData.setCommit(true);
            queueData.setData(object);

            Map<String, Object> pkIds = Maps.newHashMap();
            pkIds.put("id", "2");
            queueData.setPkIds(pkIds);

            JSONObject keyJson = new JSONObject();
            keyJson.put("table", queueData.getTable());
            keyJson.put("database", queueData.getDatabase());
            pkIds.forEach((k, v) -> {
                keyJson.put("pk." + k, v);
            });
            send(JSON.toJSONString(keyJson), JSON.toJSONString(queueData));
        }


    }

    @Test
    public void testUpdate() throws JSONException {

        for (int i = 0; i < 1; i++) {
            JSONObject object = new JSONObject();

            object.put("id", "1");
            object.put("name", "王五");
            QueueData<JSONObject> queueData = new QueueData();
            queueData.setDatabase("demo");
            queueData.setTable("test");
            queueData.setType("DMLOperateType.UPDATE");
            queueData.setTs(System.currentTimeMillis());
            queueData.setCommit(true);
            queueData.setData(object);

            Map<String, Object> pkIds = Maps.newHashMap();
            pkIds.put("id", "1");
            queueData.setPkIds(pkIds);

            JSONObject keyJson = new JSONObject();
            keyJson.put("table", queueData.getTable());
            keyJson.put("database", queueData.getDatabase());
            pkIds.forEach((k, v) -> {
                keyJson.put("pk." + k, v);
            });
            send(JSON.toJSONString(keyJson), JSON.toJSONString(queueData));
        }


    }

    @Test
    public void testDelete() throws JSONException {

        for (int i = 0; i < 1; i++) {
            JSONObject object = new JSONObject();

            object.put("id", "1");
            QueueData<JSONObject> queueData = new QueueData();
            queueData.setDatabase("demo");
            queueData.setTable("test");
            queueData.setType("DMLOperateType.DELETE");
            queueData.setTs(System.currentTimeMillis());
            queueData.setCommit(true);
            queueData.setData(object);

            Map<String, Object> pkIds = Maps.newHashMap();
            pkIds.put("id", "1");
            queueData.setPkIds(pkIds);

            JSONObject keyJson = new JSONObject();
            keyJson.put("table", queueData.getTable());
            keyJson.put("database", queueData.getDatabase());
            pkIds.forEach((k, v) -> {
                keyJson.put("pk." + k, v);
            });
            send(JSON.toJSONString(keyJson), JSON.toJSONString(queueData));
        }


    }

    @Test
    public void testinsert() {
//        k:{"table":"stg_event_var_ccreditikifhtrjf"},
//        v:{"commit":false,"data":{"field_score":0.0,"ab_path_4":"","ab_path_1":"","field_code":"jkgjjdwjcbl","ab_path_3":"","ab_path_2":"","field_bid":"o5676","store_time":1603431655942,"field_value":"0.05","ab_path_2_name":"","class":"jrx.anyest.engine.base.strategy.event.model.StrategyResultVariable","ab_path_4_name":"","ab_path_1_name":"","field_name":"接口公积金单位缴存比例","parent_field_name":"","extra_1":"","ab_path_3_name":"","extra_2":"","event_id":"160343165234560653661","extra_3":"","parent_field":"","field_type":"CONDITION_FIELD","event_time":1603431652345,"field_object_code":"5392"},"database":"","offset":0,"partition":0,"table":"stg_event_var_ccreditikifhtrjf","ts":0,"type":"INSERT"}

        String key = "{\"table\":\"stg_event_var_ccreditikifhtrjf\"}";
        String value = " {\"commit\":false,\"data\":{\"field_score\":0.0,\"ab_path_4\":\"\",\"ab_path_1\":\"\",\"field_code\":\"jkgjjdwjcbl\",\"ab_path_3\":\"\",\"ab_path_2\":\"\",\"field_bid\":\"o5676\",\"store_time\":1603431655942,\"field_value\":\"0.05\",\"ab_path_2_name\":\"\",\"class\":\"jrx.anyest.engine.base.strategy.event.model.StrategyResultVariable\",\"ab_path_4_name\":\"\",\"ab_path_1_name\":\"\",\"field_name\":\"接口公积金单位缴存比例\",\"parent_field_name\":\"\",\"extra_1\":\"\",\"ab_path_3_name\":\"\",\"extra_2\":\"\",\"event_id\":\"160343165234560653661\",\"extra_3\":\"\",\"parent_field\":\"\",\"field_type\":\"CONDITION_FIELD\",\"event_time\":1603431652345,\"field_object_code\":\"5392\"},\"database\":\"\",\"offset\":0,\"partition\":0,\"table\":\"stg_event_var_ccreditikifhtrjf\",\"ts\":0,\"type\":\"INSERT\"}";
        send(key, value);
    }

    public static void send(String key, String msg) {
        producer.beginTransaction();
        log.info("发送数据 key:{},msg:{}", key, msg);
        producer.send(new ProducerRecord<String, String>(TOPIC, key, msg));
        producer.commitTransaction();
    }

    class QueueData <T>{
        private String database;
        private String table;
        private String type;
        private Long ts;
        private boolean commit;
        private JSONObject data;
        private Map<String, Object> pkIds;

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }

        public String getTable() {
            return table;
        }

        public void setTable(String table) {
            this.table = table;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Long getTs() {
            return ts;
        }

        public void setTs(Long ts) {
            this.ts = ts;
        }

        public boolean isCommit() {
            return commit;
        }

        public void setCommit(boolean commit) {
            this.commit = commit;
        }

        public JSONObject getData() {
            return data;
        }

        public void setData(JSONObject data) {
            this.data = data;
        }

        public Map<String, Object> getPkIds() {
            return pkIds;
        }

        public void setPkIds(Map<String, Object> pkIds) {
            this.pkIds = pkIds;
        }
    }

}
