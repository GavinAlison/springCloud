package com.alison.datasource.util;

import com.alison.datasource.config.DataSourceConfigEntity;
import com.alison.datasource.config.HandlerDataSource;
import com.alison.datasource.config.TenantInfo;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @program: anyest-rule-engine-new
 * @description: ${description}
 * @author: yuanchangyou
 * @create: 2020-06-13 14:26
 **/
@Slf4j
public class DataSourceUtils {


    public static DataSourceConfigEntity getEntitieByDefaultDataSource(TenantInfo tenantInfo) {
        HandlerDataSource handlerDataSource = SpringUtil.getBean(HandlerDataSource.class);
        HikariDataSource defaultDataSource = null;
        try {
            Field defaultTargetDataSource = handlerDataSource.getClass()
                    .getSuperclass().getDeclaredField("defaultTargetDataSource");
            defaultTargetDataSource.setAccessible(true);
            defaultDataSource = (HikariDataSource)defaultTargetDataSource.get(handlerDataSource);

        } catch (Exception e) {
            log.error("get Entitle By Default DataSource error, ", e);
        }
        String jdbcUrl = defaultDataSource.getJdbcUrl().replaceAll("(3306/)(.*)[?]", "3306/{dataBaseName}?");
        DataSourceConfigEntity dataSourceConfigEntity = new DataSourceConfigEntity();
        dataSourceConfigEntity.setCode(tenantInfo.getTenantNo());
        dataSourceConfigEntity.setUrl(jdbcUrl.replace("{dataBaseName}",tenantInfo.getMysqlDbName()));
        dataSourceConfigEntity.setId(tenantInfo.getTenantNo());
        dataSourceConfigEntity.setUserName(defaultDataSource.getUsername());
        dataSourceConfigEntity.setPwd(defaultDataSource.getPassword());
        dataSourceConfigEntity.setDriverClass(defaultDataSource.getDriverClassName());

        return dataSourceConfigEntity;
    }

}
