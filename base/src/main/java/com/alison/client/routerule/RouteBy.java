package com.alison.client.routerule;

import java.lang.annotation.*;

/**
 * 路由参数 （按照调用方法时的哪个参数做路由匹配）
 * @author yxy
 * @version 2.0
 * @date 2019/10/20
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RouteBy {

     RouteParamType paramType() default RouteParamType.REQUESTPARAM;

     /***
      * 如果
      * @return
      */
     String routeColumnName() default "";


     /***
      * 路由参数类型
      * 暂时 只支持requestparam 和  requestbody  类型
      * REQUESTBODY  必须是Map类型
      *
      */
     enum RouteParamType{

          REQUESTPARAM,
          /***
           * 如果路由字段在RequestBody中,RequestBody需要 是  Map
           */
          REQUESTBODY

     }

}




