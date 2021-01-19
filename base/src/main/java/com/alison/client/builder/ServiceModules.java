package com.alison.client.builder;

import lombok.Getter;

/**
 * @program: anyest-rule-engine-new
 * @description: ${description}
 * @author: yuanchangyou
 * @create: 2019-11-29 16:38
 **/
@Getter
public enum ServiceModules {



    /***
     * 管理平台
     */
    ANYEST_CENTER_ADMIN("ANYEST-CENTER-ADMIN"),

    /**
     * 决策平台
     */
    ANYEST_ENGINE_SERVER("ANYEST-ENGINE-SERVER"),
    /**
     * 资信平台
     */
    ANYEST_DATA_SERVICE("ANYEST-DATA-SERVICE"),

    /**
     * 统计模型计算节点
     */
    ANYEST_STAT_SERVICE("ANYEST-STAT-SERVICE"),

    /**
     * 策略存储节点
     */
    ANYEST_SAVE_SERVICE ("ANYEST-SAVE-SERVICE"),

    /**
     * 数据仓库服务
     */
    ANYEST_DATAWAREHOUSE_SERVER("ANYEST-DATAWAREHOUSE-SERVER"),


    /**
     * 调度服务
     */
    ANYEST_SCHEDULE_SERVER("ANYEST-SCHEDULE-SERVER"),

    /**
     * 决策异步服务
     */
    ANYEST_STRATEGY_ASYNC_SERVER("ANYEST-STRATEGY-ASYNC-SERVER"),

    /**
     * 三方服务
     */
    ANYEST_THIRD_API_SERVER("ANYEST-THIRD-API-SERVER"),

    /**
     * 网关服务
     */
    GATEWAY_SERVER("GATEWAY-SERVER"),

    /**
     * anyTask 服务
     */
    ANYTASK_SERVER("ANYTASK-SERVER");


    private String serviceName;

    ServiceModules(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    /***
     * 按serviceName获取对应的ServiceModules
     * @param serviceName
     * @return
     */
   public static ServiceModules getServiceModule(String serviceName){
        for(ServiceModules item:values()){
            if(item.getServiceName().equals(serviceName)){
                return item;
            }
        }
        return null;

    }

  public  static class ServiceModule {

        /**
         * 管理平台
         */
        public static final String ANYEST_CENTER_ADMIN = "ANYEST-CENTER-ADMIN";
        /**
         * 决策平台
         */
        public static final String ANYEST_ENGINE_SERVER = "ANYEST-ENGINE-SERVER";
        /**
         * 资信平台
         */
        public static final String ANYEST_DATA_SERVICE = "ANYEST-DATA-SERVICE";

        /**
         * 统计模型计算节点
         */
        public static final String ANYEST_STAT_SERVICE = "ANYEST-STAT-SERVICE";

        /**
         * 策略存储节点
         */
        public static final String ANYEST_SAVE_SERVICE = "ANYEST-SAVE-SERVICE";

//    /**
//     * 数据采集
//     */
//    public static final String ANYEST_COLLECT_SERVER = "ANYEST-COLLECT-SERVER";


        /**
         * 批量调度
         */
        public static final String ANYEST_SCHEDULE_SERVER = "ANYEST-SCHEDULE-SERVER";


        /**
         * 数仓同步服务
         */
        public static final String ANYEST_DATAWAREHOUSE_SERVER = "ANYEST-DATAWAREHOUSE-SERVER";

        /**
         * 决策异步服务
         */
        public static final String ANYEST_STRATEGY_ASYNC_SERVER = "ANYEST-STRATEGY-ASYNC-SERVER";

      /**
       * 三方服务
       */
       public static final String ANYEST_THIRD_API_SERVER = "ANYEST-THIRD-API-SERVER";

      /**
       * 网关服务
       */
      public static final String GATEWAY_SERVER = "GATEWAY-SERVER";


      /****
       * anytask 服务
       */
      public static final String ANYTASK_SERVER = "ANYTASK-SERVER";


    }

}





