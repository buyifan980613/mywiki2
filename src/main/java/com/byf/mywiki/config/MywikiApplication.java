package com.byf.mywiki.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;



@ComponentScan("com.byf")
@SpringBootApplication
@MapperScan("com.byf.mywiki.mapper")
@EnableScheduling
public class MywikiApplication extends SpringBootServletInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(MywikiApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MywikiApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MywikiApplication.class);
    }

}
