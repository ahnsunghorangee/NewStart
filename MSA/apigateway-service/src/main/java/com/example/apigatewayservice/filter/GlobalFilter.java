package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> { // GlobalFilter: 모든 라우터에서 실행되는 공통 필터

    public GlobalFilter(){ // 생성자
        super(Config.class);
    }

    @Data
    public static class Config{ // 필요한 환경설정 정보(application.yml에서 등록가능), <GlobalFilter.Config>에서 GlobalFilter inner 클래스 Config
        private String baseMessage;
        private boolean preLogger; // boolean 타입은 is로 시작되는 함수를 Lombok에서 만들어준다.
        private boolean postLogger;
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Custom pre filter
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Global Filter base Message: {}", config.getBaseMessage());

            if(config.isPreLogger()){
                log.info("Global Filter Start: request id -> {}", request.getId());
            }

            // Custom Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if(config.isPostLogger()){
                    log.info("Global Filter End: response id -> {}", response.getStatusCode());
                }
            }));
        };
    }
}
