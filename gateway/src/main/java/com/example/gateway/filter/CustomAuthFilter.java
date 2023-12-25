package com.example.gateway.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.example.gateway.jwt.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomAuthFilter  extends AbstractGatewayFilterFactory<CustomAuthFilter.Config> {

    @Autowired
    private JwtProperties jwtProperties;

    public CustomAuthFilter() {
        super(Config.class);
    }
    //jwt 검증
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            //log.info("실행됨 :{}", exchange.getRequest().getHeaders().getFirst("Authorization"));
            String token = extractToken(exchange.getRequest().getHeaders().getFirst("Authorization"));
            if(token == null){
                return chain.filter(exchange);
            }
            //log.info("token = {}", token);

            //검증 로직
            String username = JWT.require(Algorithm.HMAC512("janack_overflow_overflow_overflow_overflow_overflow_overflow_overflow_overflow_overflow_overflow"))
                    .build()
                    .verify(token)
                    .getClaim("email")
                    .asString();


            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                //log.info("Custom POST filter : token -> {}", exchange.getResponse());
            }));
        });

    }
    public String extractToken(String header){
        if(header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.replace("Bearer ", "");
    }
//    public boolean validateToken(String token){
//
//    }

    public static class Config {

    }
}
