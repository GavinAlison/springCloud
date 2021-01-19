package com.alison.client.routerule;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * feign请求拦截器
 * 所有用feign发出的请求的拦截器
 */
@Configuration
@Slf4j
@ConditionalOnProperty(prefix = "anyest.client",name="route-by-thread-name",havingValue = "true")
public class FeignRequestThreadInterceptor implements RequestInterceptor {

    @Autowired
    private RouteValue routeValue;
    /**
     * 设置为按线程名称（线程名称 由 具体的路由分发值赋予）路由转发不同的资信平台
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //这里可以添加feign请求的全局参数
        //按线程名称路由，同一线程的数据发送到同一个资信平台

        String routeValue = this.routeValue.getRouteValue();

        log.info("feign的路由值为:{}",routeValue);

        requestTemplate.header(RouteKeys.THREAD_NAME_KEY,routeValue);

    }
}