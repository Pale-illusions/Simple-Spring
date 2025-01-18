package com.iflove.simplespring.boot.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Web的自动配置类
 */

@Configuration
public class WebServerAutoConfiguration {

    @Bean
    public TomcatWebServer tomcatWebServer() {
        return new TomcatWebServer();
    }

}
