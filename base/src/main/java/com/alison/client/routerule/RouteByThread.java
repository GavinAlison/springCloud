package com.alison.client.routerule;

/**
 * @program: anyest-rule-engine-new
 * @description: 按线程名进行路由
 * @author: yuanchangyou
 * @create: 2020-04-11 21:34
 **/
public class RouteByThread implements RouteValue{

    ThreadLocal<String> localRouteValue = new ThreadLocal<>();

    @Override
    public void setRouteValue(String value) {
        localRouteValue.set(value);
    }

    @Override
    public String getRouteValue() {
        String routeValue = localRouteValue.get();
        if(routeValue == null){
            routeValue = String.valueOf(Thread.currentThread().getId());
        }
        return routeValue;

    }
}
