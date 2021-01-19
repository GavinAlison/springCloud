package com.alison.server;

public class Test {
    public static void main(String[] args) {
        String topicPrex = "", topic="3_dml_maxwell_compensation";
        if(topic.contains(DataConstants.MAXWELL_DML_COMPEN_TOPIC)){
            topicPrex = topic.replace(DataConstants.MAXWELL_DML_COMPEN_TOPIC, "");
        }else if(topic.contains(DataConstants.MAXWELL_DML_TOPIC)){
            topicPrex = topic.replace(DataConstants.MAXWELL_DML_TOPIC, "");
        }
        String s = topicPrex;
        System.out.println(s);
    }
}

final class DataConstants {

    public static final String STITCHING_CHAR = "@_@";
    public static final String MAXWELL_DDL_TOPIC = "_ddl_maxwell";
    public static final String MAXWELL_DML_TOPIC = "_dml_maxwell";
    public static final String MAXWELL_DML_COMPEN_TOPIC = "_dml_maxwell_compensation";// 事务补偿topic


}

