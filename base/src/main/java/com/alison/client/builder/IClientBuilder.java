
package com.alison.client.builder;


/**
 * @program: anyest-rule-engine-new
 * @description: 远程客户端Builder
 * @author: yuanchangyou
 * @create: 2019-12-13 16:33
 **/
public interface IClientBuilder {


    /***
     * 手动创建FeignClient，获得特定服务上的feign 客户端，负载均衡FeignClient
     * @param clazz
     * @returnTT
     */
    <T> T getClientBean(Class<T> clazz, ServiceModules serviceModule,int conectTimeoutMillis,int readTimeoutMillis);

    /***
     * 手动创建FeignClient，获取注解上的ServiceModule，负载均衡FeignClient
     * @param clazz
     * @returnTT
     */
    <T> T getClientBean(Class<T> clazz,int conectTimeoutMillis,int readTimeoutMillis);

    /***
     * 手动创建FeignClient，获取注解上的ServiceModule，负载均衡FeignClient
     * @param clazz
     * @returnTT
     */
    <T> T getClientBean(Class<T> clazz);

    /***
     * 手动创建FeignClient，获取注解上的ServiceModule，负载均衡FeignClient
     * @param clazz
     * @returnTT
     */
    <T> T getClientBean(Class<T> clazz, ServiceModules serviceModule);


    /***
     * 通过Spring获取
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getClientBeanBySpringBean(Class<T> clazz);

    /***
     * 手动创建FeignClient,指定url，不做负载均衡
     * 指定特定的url
     * @param clazz
     * @param serviceModule
     * @returnTT
     */
      <T> T getClientBean(Class<T> clazz, ServiceModules serviceModule,String url,int conectTimeoutMillis,int readTimeoutMillis);
}