package com.example.redisspringboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

//@EnableAutoConfiguration
//@ComponentScan
@EnableCaching
@EnableConfigurationProperties
//@ComponentScan(basePackages = {"com.baidu","com.example"})
//@PropertySource({"classpath:application.properties"})
@SpringBootApplication
public class RedisSpringbootApplication {
    private static final Logger logger = LoggerFactory.getLogger(RedisSpringbootApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RedisSpringbootApplication.class, args);
        logger.debug("启动成功~~~~");
	}
}
