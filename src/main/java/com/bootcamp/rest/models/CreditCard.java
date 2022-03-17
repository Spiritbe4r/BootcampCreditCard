package com.bootcamp.rest.models;

import jdk.jfr.DataAmount;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "creditCard")
public class CreditCard {

    @Id
    private String id;
}
