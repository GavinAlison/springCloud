
package com.alison.client.tenantid;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alison.client.config.TenantConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author Songyc5
 * @Date: 2020/6/12
 */
@Slf4j
public class TenantIdProvider {

    private static TransmittableThreadLocal<String> tenantIdHolder = new TransmittableThreadLocal<>();


    public static final String KEY_TENANT_ID_IN_HTTP_HEADER = "tenant_id";

    public static String getTenantId() {
        return tenantIdHolder.get();
    }

    public static void setTenantId(String tenantId) {
        tenantIdHolder.set(tenantId);
    }

    public static void clearTenantId() {
//        tenantIdHolder.remove();
    }

    /**
     * 租户id无效？
     *
     * @return
     */
    public static boolean checkTenantIdIsInvalid() {
        return checkTenantIdIsInvalid(getTenantId());
    }

    public static boolean checkTenantIdIsInvalid(String tenantId) {
        return StringUtils.isBlank(tenantId) || !TenantConfigProperties.tenantInfoList.containsKey(tenantId);
    }
}
