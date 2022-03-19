package com.bootcamp.rest.services.Impl;

import com.bootcamp.rest.models.entities.CreditCard;
import com.bootcamp.rest.models.dto.Client;
import com.bootcamp.rest.models.dto.ClientDTO;

import com.bootcamp.rest.repository.CreditCardRepository;
import com.bootcamp.rest.services.CreditCardService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class CreditCardServiceImpl implements CreditCardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardServiceImpl.class);
    private final CreditCardRepository creditCardRepo;

    private final WebClient webClient;

    @Override
    public Flux<CreditCard> getAll() {
        return creditCardRepo.findAll();
    }

    @Override
    public Mono<CreditCard> getById(String s) {
        return creditCardRepo.findById(s);
    }

    @Override
    public Mono<CreditCard> save(CreditCard obj) {
        return creditCardRepo.insert(obj);
    }

    @Override
    public Mono<CreditCard> update(Mono<CreditCard> obj, String s) {
        return creditCardRepo.findById(s)
                .doOnNext(e-> e.setId(s))
                .flatMap(creditCardRepo::save);
    }

    @Override
    public Mono<Void> delete(String s) {
        return creditCardRepo.deleteById(s);
    }

    /*@Override
    public Mono<Client> getClient(String clientIdNumber) {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("clientIdNumber",clientIdNumber);
        return webClient.get()
                .uri("/findClientCredit/"+clientIdNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Client.class))
                .doOnNext(c->LOGGER.info("Client response : {}",c.getName()));

    }*/

    @Override
    public Mono<Client> getClient(String clientIdNumber) {
        return webClient.get()
                .uri("/findClientCredit/"+clientIdNumber)
                .retrieve()
                .bodyToMono(Client.class)
                .doOnNext(c->LOGGER.info("Client response : {}",c.getName()));
    }

    @Override
    public Mono<CreditCard> findByPan(String pan) {
        return creditCardRepo.findByPan(pan);
    }

    @Override
    public Mono<ClientDTO> newPan(String id, ClientDTO clientDTO) {
        return webClient.put()
                .uri("/cards/{id}", Collections.singletonMap("id",id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(clientDTO)
                .retrieve()
                .bodyToMono(ClientDTO.class)
                .doOnNext(c->LOGGER.info("Client response : {}",c.getName()));

    }

    @Override
    public Mono<CreditCard> validateClientIdNumber(String clientIdNumber) {
        return creditCardRepo.findByClient_ClientIdNumber(clientIdNumber)
                .switchIfEmpty(Mono.just(CreditCard.builder().pan(null).build()));
    }

    @Override
    public Mono<CreditCard> findByClientIdNumber(String clientIdNumber) {
        return creditCardRepo.findByClient_ClientIdNumber(clientIdNumber);
    }
}
