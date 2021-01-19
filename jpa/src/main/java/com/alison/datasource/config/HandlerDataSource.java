package com.alison.datasource.config;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author sukang
 */
public class HandlerDataSource extends AbstractRoutingDataSource {
   public static Map<Object, Object> targetDataSources;

    private final static TransmittableThreadLocal<String> DATASOURCE_THREAD_LOCAL = new TransmittableThreadLocal<>();

    public HandlerDataSource(DataSource defaultTargetDataSource,
                             Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
        this.targetDataSources=targetDataSources;
    }



    public static Object getDataSource() {
        return DATASOURCE_THREAD_LOCAL.get();
    }
    public static Object getDataSourceById(String id) {
        return targetDataSources.get(id);
    }

    public static void setDataSource(String dataSourceKey){
        DATASOURCE_THREAD_LOCAL.set(dataSourceKey);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }

    public static void clearDataSource() {
        DATASOURCE_THREAD_LOCAL.remove();
    }
}
