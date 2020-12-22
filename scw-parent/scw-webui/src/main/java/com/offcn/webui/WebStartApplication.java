package com.offcn.webui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//不启用jdbc，不需要配置数据源
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class WebStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebStartApplication.class);
    }

}
