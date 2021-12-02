package com.eCommerce.gateway.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
public class GatewayConfig {

    @LoadBalanced
    @Bean
    public RouteLocator eCommRouteConfig(RouteLocatorBuilder builder){
        System.out.println("Im in gateway-----------------------------");
        return builder.routes()
                .route(r -> r.path("/ecomm*","/ecomm/*")
                        .filters(f->f.circuitBreaker(c->c.setName("ProviderCB")
                                                        .setFallbackUri("forward:/provider-failure")
                                                        .setRouteId("prov-failover")))
                         .uri("lb://eCommerce-order"))
                .route(r->r.path("/provider-failure/**")
                         .uri("lb://provider-failure"))
                .build();
    }
}
