package com.alison.client.routerule;

import feign.Request;
import lombok.Getter;

/**
 * @description: 路由Options 继承  Request.Options   添加 路由参数类型  和路由列名称
 * @author: yuanchangyou
 * @create: 2019-12-10 11:33
 **/

@Getter
public  class RouteOptions extends  Request.Options{

    /***
     * 路由参数类型
     */
    private RouteBy.RouteParamType routeParamType;

    /***
     * 路由参数名称
     */
    private String routeColumnName;


    public RouteOptions(int connectTimeoutMillis, int readTimeoutMillis, RouteBy.RouteParamType routeParamType, String routeColumnName) {
        super(connectTimeoutMillis, readTimeoutMillis);
        this.routeParamType = routeParamType;
        this.routeColumnName = routeColumnName;
    }


}
