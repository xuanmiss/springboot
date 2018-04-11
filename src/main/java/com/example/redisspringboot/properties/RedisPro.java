package com.example.redisspringboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author miss
 * <p>
 * Created by miss on 2018/2/6
 */
@Configuration
//@ConfigurationProperties()
@ConfigurationProperties(prefix = "spring.redis")
public class RedisPro {

    private String host;
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "RedisPro{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
