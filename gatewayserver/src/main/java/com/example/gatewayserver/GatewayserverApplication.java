package com.example.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

    @Bean
    public RouteLocator microservicesRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/microservices/accounts/**")
                        .filters(f -> f.rewritePath("/microservices/accounts/(?<segment>.*)", "/${segment}"))
                        .uri("lb://ACCOUNTS"))
                .route(p -> p
                        .path("/microservices/loans/**")
                        .filters(f -> f.rewritePath("/microservices/loans/(?<segment>.*)", "/${segment}"))
                        .uri("lb://LOANS"))
                .route(p -> p
                        .path("/microservices/cards/**")
                        .filters(f -> f.rewritePath("/microservices/cards/(?<segment>.*)", "/${segment}"))
                        .uri("lb://CARDS"))
                .build();
    }
}
