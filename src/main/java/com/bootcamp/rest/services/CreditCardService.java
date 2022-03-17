package com.bootcamp.rest.services;

import com.bootcamp.rest.models.entities.CreditCard;
import com.bootcamp.rest.models.dto.Client;
import com.bootcamp.rest.models.dto.ClientDTO;
import reactor.core.publisher.Mono;

public interface CreditCardService extends CrudService <CreditCard,String> {

    public Mono<Client> getClient(String clientIdNumber);


    public Mono<CreditCard> findByPan(String pan);

    public Mono<ClientDTO> newPan(String id, ClientDTO clientDTO);


    Mono<CreditCard> validateClientIdNumber(String clientIdNumber);

    public Mono<CreditCard> findByClientIdNumber(String clientIdNumber);
}
