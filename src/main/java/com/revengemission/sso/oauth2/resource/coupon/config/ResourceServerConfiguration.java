package com.revengemission.sso.oauth2.resource.coupon.config;

import com.revengemission.sso.oauth2.resource.coupon.persistence.mapper.ResourceEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Resource Server (default) will attempt to coerce these scopes into a list of granted authorities,
 * prefixing each scope with the string "SCOPE_".
 * To use own custom attribute, may need to adapt the attribute or a composition of attributes into internalized authorities.
 * implements Converter<Jwt, AbstractAuthenticationToken>,or extends JwtAuthenticationConverter;
 * 默认验证JWT中scope,详见JwtAuthenticationConverter,如果使用JWT中其他claim字段, 需要覆盖jwtAuthenticationConverter，参照注释
 * https://docs.spring.io/spring-security/site/docs/5.2.0.RELEASE/reference/htmlsingle/#oauth2resourceserver
 */
@Configuration
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    ResourceEntityMapper resourceEntityMapper;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .cors()
            .and()
            .csrf().disable()
            .authorizeRequests()
            .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .mvcMatchers("/cat/list").hasAnyRole("ABC", "USER")
//            .mvcMatchers("/v2/api-docs").permitAll()
//            .mvcMatchers("/webjars/**").permitAll()
//            .mvcMatchers("/swagger-resources/**").permitAll()
            .withObjectPostProcessor(new MyObjectPostProcessor())
//            .mvcMatchers("/coupon/**").hasAnyAuthority("ROLE_SUPER")
//            .mvcMatchers("/product/**").hasAnyAuthority("ROLE_USER", "ROLE_SUPER")
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer()
            .accessDeniedHandler(customAccessDeniedHandler)
            .bearerTokenResolver(new CustomTokenResolver())
            .jwt().jwtAuthenticationConverter(jwtAuthenticationConverter()).decoder(jwtDecoder());
    }


    /**
     * 默认 AccessDecisionManager 为 AffirmativeBased （一票通过）
     * web 类型项目中默认 AccessDecisionVoter 为 WebExpressionVoter
     * 其他类型项目中默认 AccessDecisionVoter 为 RoleVoter 和 AuthenticatedVoter
     */
    private class MyObjectPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {
        @Override
        public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
            fsi.setSecurityMetadataSource(new ExpressionFilterInvocationSecurityMetadataSource(fsi.getSecurityMetadataSource(), resourceEntityMapper));
            AffirmativeBased affirmativeBased = (AffirmativeBased) fsi.getAccessDecisionManager();
            affirmativeBased.getDecisionVoters().add(new ExpressionVoter());
///            affirmativeBased.getDecisionVoters().add(new RoleVoter());
            return fsi;
        }
    }

    Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        GrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new GrantedAuthoritiesConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @SuppressWarnings("unchecked")
    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(issuerUri);
        OAuth2TokenValidator<Jwt> tokenBlackListValidator = new TokenBlackListValidator();
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
        OAuth2TokenValidator<Jwt> delegatingOAuth2TokenValidator = new DelegatingOAuth2TokenValidator<>(withIssuer, tokenBlackListValidator);
        jwtDecoder.setJwtValidator(delegatingOAuth2TokenValidator);
        return jwtDecoder;
    }

}
