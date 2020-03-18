package com.revengemission.sso.oauth2.resource.coupon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Value("${app.build.time}")
    String buildTime;

    @Value("${app.version}")
    String appVersion;

    @Bean
    public Docket createRestApi() {
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder
            .name("Authorization")
            .description("Authorization")
            .modelRef(new ModelRef("string"))
            .parameterType("header")
            .description("Bearer授权模式，'Bearer '开始")
            .required(false)
            .build();

        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .ignoredParameterTypes(Principal.class)
            .ignoredParameterTypes(JwtAuthenticationToken.class)
            .globalOperationParameters(aParameters).select()
            .apis(RequestHandlerSelectors.basePackage("com.revengemission.sso.oauth2.resource.coupon.controller"))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("online APIs").description("登录获取token接口不在这里，\n"
            + "请求需要权限的接口时在请求header中添加Authorization token，采用Bearer 模式，如：\n" + "Authorization:Bearer a.b.c")
            .termsOfServiceUrl("").version(appVersion + " @" + buildTime).build();
    }
}
