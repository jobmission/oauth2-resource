package com.revengemission.sso.oauth2.resource.coupon.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * json 工具类
 */
public class JsonUtil {

    public static String objectToJsonString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
//      美化输出
///        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

//      Object to JSON in String
        return mapper.writeValueAsString(object);
    }

    public static String multiValueMapToJsonString(Map<String, String[]> object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
//        美化输出
///        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        Map<String, String> newMap = new HashMap<>(16);
        if (object != null && object.size() > 0) {
            object.forEach((k, v) -> {
                if (v != null && v.length > 0) {
                    newMap.put(k, Arrays.toString(v));
                }
            });
        }
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

//      Object to JSON in String
        return mapper.writeValueAsString(newMap);
    }

    public static <T> T jsonStringToObject(String jsonString, Class<T> t) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


//      JSON from String to Object
        return mapper.readValue(jsonString, t);
    }
}
