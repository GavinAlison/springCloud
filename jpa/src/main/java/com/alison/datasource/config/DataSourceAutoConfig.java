package com.alison.datasource.config;


import com.alison.datasource.util.DataSourceUtils;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sukang
 */
@Slf4j
@Configuration
public class DataSourceAutoConfig {

    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    @Primary
    @Bean(name = "defaultDataSource")
    public HikariDataSource firstSource(){

        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }


    @Bean(name = "routeDataSource")
    public HandlerDataSource dataSource(@Autowired HikariDataSource defaultDataSource) {
                new BeanPropertyRowMapper<>(DataSourceConfigEntity.class);

        Map<String, TenantInfo> tenantInfoList = TenantConfigProperties.tenantInfoList;

        Map<Object, Object> targetDataSources = new HashMap<>(
                CollectionUtils.isEmpty(tenantInfoList) ? 1 : tenantInfoList.size());
        tenantInfoList.forEach((tenantId,tenantInfo) -> {

            DataSourceConfigEntity dataSourceConfigEntity = null;
                    HikariDataSource hikariDataSource;
            try {
                dataSourceConfigEntity = DataSourceUtils.getEntitieByDefaultDataSource(tenantInfo);
                hikariDataSource = DataSourceBuilder.create()
                      .type(HikariDataSource.class)
                      .url(dataSourceConfigEntity.getUrl())
                      .username(dataSourceConfigEntity.getUserName())
                      .password(dataSourceConfigEntity.getPwd())
                      .driverClassName("com.mysql.cj.jdbc.Driver")
                      .build();

                setDatasourceProperties(hikariDataSource,dataSourceConfigEntity);
                //配置Hikari连接池
                targetDataSources.put(dataSourceConfigEntity.getCode(),hikariDataSource);
                Connection connection = hikariDataSource.getConnection();
                if (connection != null){
                    connection.close();
                }
            } catch (Exception e) {
                log.error("数据源code为" + dataSourceConfigEntity.getCode() + "的数据源建立异常",e);
            }
        });
        return new HandlerDataSource(defaultDataSource, targetDataSources);
    }

    private void setDatasourceProperties(HikariDataSource hikariDataSource, DataSourceConfigEntity t) {
        //配置Hikari连接池
        String code = t == null ? "default" :t.getCode();
        String id = t == null ? "1" :t.getId().toString();

        hikariDataSource.setConnectionTestQuery("select 1");//连接查询语句设置
        hikariDataSource.setAutoCommit(true);//update自动提交设置
        hikariDataSource.setConnectionTimeout(3000);//连接超时时间设置
        hikariDataSource.setIdleTimeout(3000);//连接空闲生命周期设置
        hikariDataSource.setIsolateInternalQueries(false);//执行查询启动设置
        hikariDataSource.setMaximumPoolSize(3000);//连接池允许的最大连接数量
        hikariDataSource.setMaxLifetime(1800000);//检查空余连接优化连接池设置时间,单位毫秒
        hikariDataSource.setMinimumIdle(12);//连接池保持最小空余连接数量
        hikariDataSource.setPoolName("hikariPool--".concat(id)
                .concat("--").concat(code));//连接池名称
    }

}
