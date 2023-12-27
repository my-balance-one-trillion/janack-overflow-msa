package com.example.janackoverflow.openfeign;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;

@Configuration
public class OpenFeignConfig {
    /*
    - NONE : 로깅하지 않음(DEFAULT)
    - BASIC : Request Method와 URL, Reponse 상태 코드 및 실행 시간을 로깅
    - HEADERS : Request, Response Header + BASIC 정보를 로깅
    - FULL : Request, Response의 Header, Body, 메타데이터를 로깅
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", "application/json");
            requestTemplate.header("Accept", "application/json");
        };
    }
}
