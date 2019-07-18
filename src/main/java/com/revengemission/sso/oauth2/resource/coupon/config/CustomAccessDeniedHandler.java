package com.revengemission.sso.oauth2.resource.coupon.config;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response, AccessDeniedException e)
        throws IOException, ServletException {

        String toUrl = "http://" + request.getServerName()
            + ":"
            + request.getServerPort()
            + request.getContextPath()
            + request.getServletPath()
            + (request.getQueryString() == null ? "" : ("?" + request.getQueryString()));
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            Map<String, Object> responseMessage = new HashMap<>();
            responseMessage.put("status", 403);
            responseMessage.put("message", "权限不足！");
            responseMessage.put("path", toUrl);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(response.getOutputStream(),
                JsonEncoding.UTF8);
            objectMapper.writeValue(jsonGenerator, responseMessage);
        } catch (Exception ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }
}
