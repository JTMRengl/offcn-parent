package com.offcn.project.config;

import com.offcn.utils.OssTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppProjectConfig {

    @ConfigurationProperties(prefix = "oss")
    @Bean
    public OssTemplate getOssTemplate(){
        return  new OssTemplate();
    }
}
