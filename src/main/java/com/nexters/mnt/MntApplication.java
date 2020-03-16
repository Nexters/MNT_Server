package com.nexters.mnt;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.logging.Logger;

@SpringBootApplication
@EnableSwagger2
@Slf4j
@EnableScheduling
@PropertySource("classpath:application-aws.properties")
public class MntApplication{

    public static void main(String[] args) {
        SpringApplication.run(MntApplication.class, args);
    }

}
