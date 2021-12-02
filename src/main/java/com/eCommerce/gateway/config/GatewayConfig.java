package com.eCommerce.gateway.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayConfig {

    
    @Bean
    public RouteLocator eCommRouteConfig(RouteLocatorBuilder builder){
        System.out.println("Im in gateway-----------------------------");
        return builder.routes()
                .route(r -> r.path("/ecomm/create*","/ecomm/create/*")
                        .filters(f->f.circuitBreaker(c->c.setName("ProviderCB")
                                                        .setFallbackUri("forward:/provider-failover/create/")
                                                        .setRouteId("prov-failover")))
                         .uri("lb://eCommerce-order"))
                .route(r -> r.path("/ecomm/view*","/ecomm/view/*")
                        .filters(f->f.circuitBreaker(c->c.setName("ProviderCB")
                                                        .setFallbackUri("forward:/provider-failover/view/")
                                                        .setRouteId("prov-failover")))
                         .uri("lb://eCommerce-order"))
                .route(r -> r.path("/ecomm/update*","/ecomm/update/*")
                        .filters(f->f.circuitBreaker(c->c.setName("ProviderCB")
                                                        .setFallbackUri("forward:/provider-failover/update/")
                                                        .setRouteId("prov-failover")))
                         .uri("lb://eCommerce-order"))
                .route(r -> r.path("/ecomm/cancel*","/ecomm/cancel/*")
                        .filters(f->f.circuitBreaker(c->c.setName("ProviderCB")
                                                        .setFallbackUri("forward:/provider-failover/cancel/")
                                                        .setRouteId("prov-failover")))
                         .uri("lb://eCommerce-order"))
                .build();
    }
    
 
}
