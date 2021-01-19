package com.alison.datasource.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @program: anyest-rule-engine-new
 * @description: ${description}
 * @author: yuanchangyou
 * @create: 2020-06-11 22:01
 **/
@Setter
@Getter
public class TenantInfo {

    private String tenantNo;
    private String kafkaTopicPrex;
    private String mysqlDbName;
    private String greenplumDbName;
    private String greenplumDbCode;
    private String elasticSearchPrex;


}
