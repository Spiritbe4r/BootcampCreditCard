package com.bootcamp.rest.repository;

import com.bootcamp.rest.models.entities.CreditCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CreditCardRepository extends ReactiveMongoRepository<CreditCard,String> {

    Mono<CreditCard> findByPan (String pan);

    Mono<CreditCard> findByClient_ClientIdNumber(String clientIdNumber);
}