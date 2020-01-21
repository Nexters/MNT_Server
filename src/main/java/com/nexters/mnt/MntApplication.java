package com.nexters.mnt;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.logging.Logger;

@SpringBootApplication
@EnableSwagger2
public class MntApplication{

    public static void main(String[] args) {
        SpringApplication.run(MntApplication.class, args);
        System.out.println();
    }

}
