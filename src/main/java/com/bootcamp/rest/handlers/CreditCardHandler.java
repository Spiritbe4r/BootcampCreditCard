package com.bootcamp.rest.handlers;

import com.bootcamp.rest.models.dto.ClientDTO;
import com.bootcamp.rest.models.entities.CreditCard;
import com.bootcamp.rest.services.CreditCardService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CreditCardHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardHandler.class);

    private final CreditCardService cardService;


    public Mono<ServerResponse> getAll(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(cardService.getAll(), CreditCard.class);
    }
    public Mono<ServerResponse> getCreditCard(ServerRequest request) {
        String id = request.pathVariable("id");
        return cardService.getById(id).flatMap(c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getCreditCardByClient(ServerRequest request){
        String clientIdNumber=request.pathVariable("clientIdNumber");
        return cardService.findByClientIdNumber(clientIdNumber).flatMap(c->ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(c))
                .switchIfEmpty(ServerResponse.notFound().build()));

    }

    public Mono<ServerResponse> getCreditCardByPan(ServerRequest request) {
        String pan = request.pathVariable("pan");
        return cardService.findByPan(pan).flatMap(c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createCreditCard(ServerRequest request){

        Mono<CreditCard> creditCardMono = request.bodyToMono(CreditCard.class);
        String clientIdNumber = request.pathVariable("customerIdentityNumber");

        return creditCardMono.flatMap( creditCard -> cardService.getClient(clientIdNumber)
                        .flatMap(customer -> {
                            LOGGER.info("Veamos: " + customer.getName());
                            creditCard.setClient(ClientDTO.builder().name(customer.getName())
                                    .code(customer.getClientType().getCode())
                                    .clientIdNumber(customer.getClientIdNumber()).build());
                            return cardService.validateClientIdNumber(customer.getClientIdNumber())
                                    .flatMap(creditcardFound -> {
                                        if(creditcardFound.getPan() != null){
                                            LOGGER.info("La tarjeta de crédito encontrada es: "
                                                    + creditcardFound.getPan());
                                            return Mono.empty();
                                        }else {
                                            LOGGER.info("No se encontró la cuenta ");
                                            return cardService.save(creditCard);
                                        }
                                    });
                        })
                ).flatMap( c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * Delete credit card mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> deleteCreditCard(ServerRequest request){

        String id = request.pathVariable("id");


        return cardService.delete(id)
                .flatMap(c -> ServerResponse
                        .ok().build().then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
