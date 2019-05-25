package com.revengemission.sso.oauth2.resource.coupon.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.revengemission.sso.oauth2.resource.coupon.persistence.mapper")
public class MapperScannerConfig {

}
