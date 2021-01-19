package com.alison;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@EnableConfigurationProperties(TomConfig.class) // 会注入容器，name=tom-com.alison.TomConfig
@SpringBootApplication
@Slf4j
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(App.class, args);
        configurableApplicationContext.getBeanFactory().getBean("bean1");
        log.debug("");
        configurableApplicationContext.close();
    }
}
