package com.bootcamp.rest.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {
    private String name;
    private String code;
    private String clientIdNumber;
}
