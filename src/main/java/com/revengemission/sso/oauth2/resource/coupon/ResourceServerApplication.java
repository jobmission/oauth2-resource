package com.revengemission.sso.oauth2.resource.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
 *
 * https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#oauth2resourceserver
 * */
@EnableTransactionManagement
@SpringBootApplication
public class ResourceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }
}
