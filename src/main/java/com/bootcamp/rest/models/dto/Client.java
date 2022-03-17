package com.bootcamp.rest.models.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private String name;
    private String clientIdType;
    private String clientIdNumber;
    private ClientType clientType;
}
