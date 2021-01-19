package com.alison.client;

import com.alison.client.builder.IClientBuilder;
import com.alison.client.builder.ServiceModules;
import com.alison.client.config.RemoteConfig;
import com.alison.client.exception.AnnotationTypeMisFindException;
import com.alison.client.intercepters.TenantIdFeignClientInterceptor;
import com.alison.client.routerule.FeignRequestThreadInterceptor;
import com.alison.client.routerule.RouteBy;
import com.alison.client.routerule.RouteOptions;
import com.alison.client.util.SpringUtil;
import feign.Client;
import feign.Feign;
import feign.Request;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Component;


/**
 * FeignClient 手动创建 Feign Client 对象
 *
 * @author yuanchangyou
 * @Date 2019-12-10
 */
@Component
public class FeignClientBuilder implements IClientBuilder {

    //required=false，调度启动时bean注入失败，暂时添加
    @Autowired
    private Encoder feignEncoder;
    @Autowired
    private Decoder feignDecoder;

    @Autowired
    private Client loadBalanceFeignClient;

    @Autowired
    private RemoteConfig remoteConfig;


    @Autowired(required = false)
    private FeignRequestThreadInterceptor feignRequestThreadInterceptor;

    @Autowired(required = false)
    private TenantIdFeignClientInterceptor tenantIdFeignClientInterceptor;

    private Client defaultClient = new feign.Client.Default(null, null);


    private final static String httpPrex = "http://";


    /***
     * 手动创建FeignClient，负载均衡，类可以
     * @param clazz
     * @param serviceModule
     * @returnTT
     */
    @Override
    public <T> T getClientBean(Class<T> clazz, ServiceModules serviceModule, int conectTimeoutMillis, int readTimeoutMillis) {
        Target.HardCodedTarget<T> hardCodedTarget =
                new Target.HardCodedTarget(clazz, serviceModule.getServiceName(), httpPrex + serviceModule.getServiceName());
        Request.Options options;
        RouteBy annotation = clazz.getAnnotation(RouteBy.class);
        if (annotation == null) {
            options = new Request.Options(conectTimeoutMillis, readTimeoutMillis);
        } else {
            options = new RouteOptions(conectTimeoutMillis, readTimeoutMillis, annotation.paramType(), annotation.routeColumnName());
        }
        Feign.Builder client = Feign.builder().
                contract(new SpringMvcContract())
                .encoder(feignEncoder)
                .decoder(feignDecoder)
                .client(loadBalanceFeignClient)
                .options(options);

        if (feignRequestThreadInterceptor != null) {
            client = client.requestInterceptor(feignRequestThreadInterceptor);
        }
        if (tenantIdFeignClientInterceptor != null) {
            client = client.requestInterceptor(tenantIdFeignClientInterceptor);
        }
        return client.target(hardCodedTarget);
    }

    public <T> T getClientBeanBySpringBean(Class<T> clazz) {
        T bean = SpringUtil.getBean(clazz);
        return bean;
    }

    /***
     * 手动创建FeignClient，获取注解上的ServiceModule，负载均衡FeignClient
     * @param clazz
     * @returnTT
     */
    @Override
    public <T> T getClientBean(Class<T> clazz, int conectTimeoutMillis, int readTimeoutMillis) {

        FeignClient feignClientAnnotation = clazz.getAnnotation(FeignClient.class);
        if (feignClientAnnotation == null) {
            throw new AnnotationTypeMisFindException(clazz + "类没有对应的注解：" + FeignClient.class);
        }
        ServiceModules serviceModule = ServiceModules.getServiceModule(feignClientAnnotation.name());
        return getClientBean(clazz, serviceModule, conectTimeoutMillis, readTimeoutMillis);

    }

    @Override
    public <T> T getClientBean(Class<T> clazz) {
        return getClientBean(clazz, remoteConfig.connectTimeout, remoteConfig.readTimeout);

    }

    @Override
    public <T> T getClientBean(Class<T> clazz, ServiceModules serviceModule) {
        return getClientBean(clazz, serviceModule, remoteConfig.connectTimeout, remoteConfig.readTimeout);
    }


    /***
     * 手动创建FeignClient,指定url，不做负载均衡
     * 指定特定的url
     * @param clazz
     * @param serviceModule
     * @returnTT
     */
    @Override
    public <T> T getClientBean(Class<T> clazz, ServiceModules serviceModule, String url, int conectTimeoutMillis, int readTimeoutMillis) {

        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }

        Target.HardCodedTarget<T> hardCodedTarget =
                new Target.HardCodedTarget(clazz, serviceModule.getServiceName(), url);
        Request.Options options = new Request.Options(conectTimeoutMillis, readTimeoutMillis);
        Feign.Builder client = Feign.builder().
                contract(new SpringMvcContract())
                .encoder(feignEncoder)
                .decoder(feignDecoder)
                .client(defaultClient)
                .options(options);


        if (feignRequestThreadInterceptor != null) {
            client = client.requestInterceptor(feignRequestThreadInterceptor);
        }
        if (tenantIdFeignClientInterceptor != null) {
            client = client.requestInterceptor(tenantIdFeignClientInterceptor);
        }
        return client.target(hardCodedTarget);
    }

}