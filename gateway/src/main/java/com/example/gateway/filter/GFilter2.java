package com.example.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GFilter2 implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("pre-필터기능-GFilter2");

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    System.out.println("post-필터기능-GFilter2");
                }));
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
