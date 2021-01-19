
package com.alison.client.config;

import com.alison.client.enums.DiscoveryType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;

/**
 * @program: anyest-rule-engine-feign
 * @description: Feign连接配置信息
 * @author: yuanchangyou
 * @create: 2019-11-06 16:54
 **/

@Getter
@Setter
@ConfigurationProperties("com.alison.client")
@Primary
public class RemoteConfig implements IClientConfig {

    /**
     * 是否开启客户端，默认开启
     */
    public boolean enable = true;

    /**
     * 是否允许clientApi
     */
    public boolean ableApi = true;

    /***
     * 默认 用户Id路由规则 是否启用
     */
    public boolean routeByThreadName = false;

    public DiscoveryType discoveryType = DiscoveryType.Nacos;

    public boolean grayscaleAble = false;

    public String anyestCenterAdmin = "127.0.0.1:9800";
    public String anyestEngineServer = "127.0.0.1:9520";
    public String anyestDatawarehouseServer = "127.0.0.1:9371";
    public String anyestDataService = "127.0.0.1:9370";
    public String anyestStrategyAsyncServer = "127.0.0.1:9527";

    /**
     * 超时事件
     */
    public int readTimeout = 120000;
    public int connectTimeout = 30000;
}

