package com.revengemission.sso.oauth2.resource.coupon.config;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ExampleBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class Swagger2Configuration {

    @Value("${app.build.time}")
    String buildTime;

    @Value("${app.version}")
    String appVersion;

    String tokenAuthorization = "bearer token authorization";

    @Bean
    public Docket docket() {
        RequestParameterBuilder aParameterBuilder = new RequestParameterBuilder();
        aParameterBuilder
            .name("Authorization")
            .description("bearer token, Authorization:bearer x.y.z")
            .example(new ExampleBuilder().value("bearer a.b.c").build())
            .in(ParameterType.HEADER)
            .required(false)
            .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)));

        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(aParameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .ignoredParameterTypes(Principal.class)
            .ignoredParameterTypes(JwtAuthenticationToken.class)
//            .globalRequestParameters(parameters)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.revengemission.sso.oauth2.resource.coupon.controller"))
            .paths(PathSelectors.any())
            .build()
            .securitySchemes(Arrays.asList(new ApiKey(tokenAuthorization, HttpHeaders.AUTHORIZATION, In.HEADER.name())))
            .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("online APIs").description("登录获取token接口不在这里，\n"
            + "请求需要权限的接口时在请求header中添加Authorization token，采用Bearer 模式，如：\n" + "Authorization:Bearer a.b.c")
            .termsOfServiceUrl("").version(appVersion + " @" + buildTime).build();
    }

    private List<SecurityContext> securityContexts() {
        return Arrays.asList(
            SecurityContext.builder()
                .securityReferences(securityReferences())
                .build()
        );
    }

    List<SecurityReference> securityReferences() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference(tokenAuthorization, authorizationScopes));
    }
}
