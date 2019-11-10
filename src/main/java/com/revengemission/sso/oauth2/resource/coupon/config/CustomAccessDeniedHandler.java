package com.revengemission.sso.oauth2.resource.coupon.config;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revengemission.sso.oauth2.resource.coupon.utils.ClientIpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response, AccessDeniedException e) throws IOException {

        String toUrl = ClientIpUtils.getFullRequestUrl(request);
        log.info("【" + request.getRequestURI() + "】权限不足 FORBIDDEN");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        Map<String, Object> responseMessage = new HashMap<>(16);
        responseMessage.put("status", HttpStatus.FORBIDDEN.value());
        responseMessage.put("message", "权限不足！");
        responseMessage.put("path", toUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(response.getOutputStream(),
            JsonEncoding.UTF8);
        objectMapper.writeValue(jsonGenerator, responseMessage);
    }
}
