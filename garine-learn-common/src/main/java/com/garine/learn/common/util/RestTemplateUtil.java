package com.garine.learn.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * ${DESCRIPTION}
 *
 * @author wugx
 * @date 2018/1/2 16:39
 */
public class RestTemplateUtil {


    /**
     * post 请求
     */
    public static <T> T post(String url, Object requestBean, Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.setContentType(type);
        headers.setAccept(MediaType.parseMediaTypes(MediaType.APPLICATION_JSON_VALUE));
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
        Gson gson = gb.create();
        HttpEntity<String> formEntity = new HttpEntity<>(gson.toJson(requestBean), headers);
        return restTemplate.postForObject(url, formEntity, clazz);
    }
}
