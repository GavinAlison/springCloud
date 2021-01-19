package com.alison.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// 将类定义为一个bean的注解，比如 @Component,@Service,@Controller,@Repository
// 或者 @Configuration
@Component
// 表示使用配置文件中前缀为 section1 的属性的值初始化该bean定义产生的的bean实例的同名属性
// 在使用时这个定义产生的bean时，其属性 name 会是 Tom
@ConfigurationProperties(prefix = "section1")
@Getter
@Setter
public class Bean1 {
    private String name;
}