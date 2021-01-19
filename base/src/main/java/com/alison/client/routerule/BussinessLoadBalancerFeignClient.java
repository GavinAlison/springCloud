package com.alison.client.routerule;

import com.alibaba.fastjson.JSON;
import com.alison.client.enums.DiscoveryType;
import com.alison.client.config.RemoteConfig;
import com.google.common.collect.Maps;
import feign.Client;
import feign.Request;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 * @author yuanchangyou
 * @Date 2019-12-10
 *  FeignClient  路由器
 *
 */
@Slf4j
public class BussinessLoadBalancerFeignClient extends LoadBalancerFeignClient {

    /***
     *普通的FeignClient
     *
     */
    private Client client;

    private RemoteConfig remoteConfig;
    private DiscoveryClient discoveryClient;

    private Client delegate;
    private CachingSpringLoadBalancerFactory lbClientFactory;
    private SpringClientFactory clientFactory;



    /***
     * key 为 serviceName  value 的key 为 index  和对应的serviceId
     */
    private Map<String,Map<Integer,ServiceInstance>> allServiceIndexMap;
    private Map<String,Map<String,Integer>> allSserviceInstanceIdAndIndexMap;

    public BussinessLoadBalancerFeignClient(Client delegate,
                                            CachingSpringLoadBalancerFactory lbClientFactory,
                                            SpringClientFactory clientFactory,
                                            RemoteConfig remoteConfig,
                                            DiscoveryClient discoveryClient) {
        super(delegate, lbClientFactory, clientFactory);
        this.delegate = delegate;
        this.lbClientFactory = lbClientFactory;
        this.clientFactory = clientFactory;
        client = new Default(null, null);
        this.remoteConfig = remoteConfig;
        this.discoveryClient = discoveryClient;
        allServiceIndexMap = Maps.newConcurrentMap();
        allSserviceInstanceIdAndIndexMap = Maps.newConcurrentMap();
        log.info("business feign 负载均衡器初始化");
    }

    /**
     *
     * @param request
     * @param options
     * @return
     */
    @Override
    public Response execute(Request request, Request.Options options) throws IOException {



        String url = request.url();
        URI uri = URI.create(url);
        String clientName = uri.getHost();
        //请求的客户名称转为小写
        clientName = clientName.toUpperCase();


        // 获取 服务名的 服务信息
        List<ServiceInstance> instances = discoveryClient.getInstances(clientName);

        if (instances == null ||instances.isEmpty() || instances.size() <= 1) {
            if(remoteConfig.getDiscoveryType() == DiscoveryType.Simple && instances.size()==1){
                ServiceInstance serviceInstance = instances.get(0);
                String instanceId = serviceInstance.getHost()+":"+serviceInstance.getPort();
                String newUrl = url.replaceFirst(clientName,instanceId);
                //
                log.debug("discoveryType:{},url:{}",remoteConfig.getDiscoveryType(),newUrl);
                return this.getResponse(request, options, newUrl);
            }
            return super.execute(request,options);
        }

        mergeInstances(clientName, instances);


        //记录 路由key和路由value
        String routeKey="",routeValue="";

        Map<String, Collection<String>> headers = request.headers();

        if(this.remoteConfig.routeByThreadName){
            if(headers.containsKey(RouteKeys.THREAD_NAME_KEY)){

                List<String> collect = headers.get(RouteKeys.THREAD_NAME_KEY).stream().collect(Collectors.toList());
                routeValue = collect.get(0);
                routeKey = RouteKeys.THREAD_NAME_KEY;

            }
        }else  if(options instanceof RouteOptions){

            RouteOptions routeOptions = (RouteOptions) options;

            if (routeOptions != null ) {
                switch (routeOptions.getRouteParamType()){
                    case REQUESTPARAM:
                        Map<String, String> requestParamMap = getRequestParamMap(url);
                        if(requestParamMap != null && requestParamMap.containsKey(routeOptions.getRouteColumnName())){
                            routeKey = routeOptions.getRouteColumnName();
                            routeValue = requestParamMap.get(routeOptions.getRouteColumnName());
                        }
                        break;
                }
            }
        }

        if(StringUtils.isEmpty(routeValue)){
            return super.execute(request,options);
        }

        ServiceInstance serviceInstance = hashKeyChoose(clientName, routeValue);
        String instanceId = serviceInstance.getHost()+":"+serviceInstance.getPort();
        String newUrl = url.replaceFirst(clientName,instanceId);
        log.info("路由匹配key:{},value:{},分发地址:{}",routeKey,routeValue,newUrl);
        return this.getResponse(request, options, newUrl);
    }




    /**
     * 请求响应
     *
     * @param request
     * @param options
     * @param newUrl
     * @return
     * @throws IOException
     */
    private Response getResponse(Request request, Request.Options options, String newUrl) throws IOException {
        //重新构建 request　对象
        Request newRequest = Request.create(request.method(),
                newUrl, request.headers(), request.body(),
                request.charset());
        return client.execute(newRequest,options);
//        return super.execute(newRequest, options);
    }


    /**
     *
     * <p>Description:使用key的hash值，和服务实例数量求余，选择一个服务实例 </p>
     * @param key
     * @return
     * @author wgs
     * @date  2019年3月15日 下午2:25:36
     *
     */
    private ServiceInstance hashKeyChoose(String clientName, Object key) {

        Map<Integer, ServiceInstance> serviceIndexMap = allServiceIndexMap.get(clientName);
        int hashCode = Math.abs(key.hashCode());
        int index = hashCode;
        if (hashCode >= serviceIndexMap.size()) {
            index = hashCode %  serviceIndexMap.size();
        }
        log.info("route Value:{},hashCode:{},index:{}",key,hashCode,index);
        ServiceInstance serviceInstance = serviceIndexMap.get(index);
        if(serviceInstance != null){
            return serviceInstance;
        }

        return serviceIndexMap.values().stream().filter(item -> item != null).findFirst().get();

    }


    /***
     * 获得url中的 RequestParam
     * @param url
     * @return
     */
    private Map<String,String> getRequestParamMap(String url){
        Map<String,String> paramMap = Maps.newHashMap();
        if(url.contains("?")){
            String[] split = url.split("\\?");
            String paramStr = split[1];
            String[] params;
            if(paramStr.contains("&")){
                params = paramStr.split("&");
            }else{
                params = new String[]{paramStr};
            }
            for(String param:params){
                String[] split1 = param.split("=");
                if(split1.length == 2){
                    paramMap.put(split1[0],split1[1]);
                }
            }

        }else return null;

        return paramMap;
    }


    /****
     * 防止 ServiceInstance 顺序发数改变
     * 服务上下线时防止 数据路由转发发生错位
     *
     * @param clientName
     * @param instances
     */
    private void mergeInstances(String clientName,List<ServiceInstance> instances) {


        Map<Integer, ServiceInstance> serviceIndexMap = allServiceIndexMap.computeIfAbsent(clientName,k-> Maps.newHashMap());
        Map<String, Integer> instanceId2IndexMap = allSserviceInstanceIdAndIndexMap.computeIfAbsent(clientName, k->Maps.newHashMap());

        int serviceInstanceIdSize = (int)serviceIndexMap.values().stream().filter(item ->item != null).count();
        if(instances.size() == serviceInstanceIdSize){
            return;
        }
        try {
            if(instances.size() > serviceInstanceIdSize){
                for(ServiceInstance item:instances){
                    if(!instanceId2IndexMap.containsKey(item.getInstanceId())){
                        int index = instanceId2IndexMap.size();
                        instanceId2IndexMap.put(item.getInstanceId(),index);
                        serviceIndexMap.put(serviceIndexMap.size(),item);
                    }else if(!serviceIndexMap.containsKey(instanceId2IndexMap.get(item.getInstanceId()))){
                        serviceIndexMap.put(instanceId2IndexMap.get(item.getInstanceId()),item);
                    }

                }
            }else{
                serviceIndexMap.clear();
                for(ServiceInstance item :instances){
                    if(instanceId2IndexMap.containsKey(item.getInstanceId())){
                        serviceIndexMap.put(instanceId2IndexMap.get(item.getInstanceId()),item);
                    }

                }
            }
        }finally {
        }

        log.info("serviceIndexMap---------->:{}", JSON.toJSONString(serviceIndexMap));

    }



}