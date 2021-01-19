package com.alison.datasource.config;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class TenantConfigProperties {

    /**
     * {
     * "101": {
     * "greenplumDbCode": "System_GP_DataSource",
     * "greenplumDbName": "anyest3_financial_cloud_101",
     * "kafkaTopicPrex": "101",
     * "mysqlDbName": "anyest3_financial_cloud_100001",
     * "tenantNo": "101"
     * },
     * "2": {
     * "greenplumDbCode": "System_GP_DataSource",
     * "greenplumDbName": "anyest3_financial_cloud_2",
     * "kafkaTopicPrex": "2",
     * "mysqlDbName": "anyest3_financial_cloud_2",
     * "tenantNo": "2"
     * },
     * "3": {
     * "greenplumDbCode": "System_GP_DataSource",
     * "greenplumDbName": "anyest3_financial_cloud_3",
     * "kafkaTopicPrex": "3",
     * "mysqlDbName": "anyest3_financial_cloud_3",
     * "tenantNo": "3"
     * },
     * "4": {
     * "greenplumDbCode": "System_GP_DataSource",
     * "greenplumDbName": "anyest3_financial_cloud_4",
     * "kafkaTopicPrex": "4",
     * "mysqlDbName": "anyest3_financial_cloud_4",
     * "tenantNo": "4"
     * }
     * }
     */
    public static Map<String, TenantInfo> tenantInfoList = Maps.newConcurrentMap();
}
