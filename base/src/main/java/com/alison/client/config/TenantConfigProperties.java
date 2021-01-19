package com.alison.client.config;

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

    public static Map<String, TenantInfo> tenantInfoList = Maps.newConcurrentMap();
}
