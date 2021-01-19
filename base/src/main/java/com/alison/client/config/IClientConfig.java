package com.alison.client.config;


/**
 * @author peidong.meng
 * @date 2019/12/17
 */
public interface IClientConfig {

    default int getReadTimeout(){
        return 30000;
    }

    default int getConnectTimeout(){
        return 120000;
    }
}
