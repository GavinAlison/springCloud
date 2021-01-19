package com.alison.client.intercepters;

import com.alison.client.tenantid.TenantIdProvider;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Songyc5
 * @Date: 2020/6/12
 */
@Slf4j
public class TenantIdFeignClientInterceptor implements RequestInterceptor {

    public TenantIdFeignClientInterceptor() {
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        //从应用上下文中取出user信息，放入Feign的请求头中
        String tenantId = TenantIdProvider.getTenantId();
        if (tenantId != null) {
            requestTemplate.header(TenantIdProvider.KEY_TENANT_ID_IN_HTTP_HEADER, tenantId);
            if (log.isDebugEnabled()) {
                log.debug("设置本次请求的租户号:{},请求URL:{}", tenantId, requestTemplate.url());
            }
        } else {
            log.warn("租户号为空");
        }
    }
}
