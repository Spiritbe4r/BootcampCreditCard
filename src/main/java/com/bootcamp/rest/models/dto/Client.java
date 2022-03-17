package com.bootcamp.rest.models.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private String name;
    private String customerIdType;
    private String customerIdNumber;
    private ClientType clientType;
}
