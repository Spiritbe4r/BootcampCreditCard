package com.bootcamp.rest.routers;

import com.bootcamp.rest.handlers.CreditCardHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CreditCardRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(CreditCardHandler creditCardHandler) {
        return route(GET("/api/creditcard"), creditCardHandler::getAll)
                .andRoute(GET("/api/creditcard/payment/{pan}"), creditCardHandler::getCreditCardByPan)
                .andRoute(GET("/api/creditcard/{id}"), creditCardHandler::getCreditCard)
                .andRoute(GET("/api/creditcard/client/{clientIdNumber}"), creditCardHandler::getCreditCardByClient)
                .andRoute(RequestPredicates.POST("/api/creditcard/{customerIdentityNumber}"), creditCardHandler::createCreditCard)
                //.andRoute(RequestPredicates.PUT("/api/creditcard/{id}"), creditCardHandler::updateCreditCard)
                .andRoute(RequestPredicates.DELETE("/api/creditcard/{id}"), creditCardHandler::deleteCreditCard);
        }
    }