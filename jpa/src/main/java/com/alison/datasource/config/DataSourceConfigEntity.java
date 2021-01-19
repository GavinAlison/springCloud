package com.alison.datasource.config;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataSourceConfigEntity {

    private String id;

    private String code;

    private String url;

    private String userName;

    private String pwd;

    private String driverClass;

    private String remarks;




}
