package com.bootcamp.rest.models.entities;
import javax.validation.constraints.NotNull;

import com.bootcamp.rest.models.dto.ClientDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.jfr.DataAmount;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "creditCard")
public class CreditCard {

    @Id
    private String id;

    private String pan;

    @NotNull
    private String cardType;

    @Field( name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOperation = LocalDateTime.now();

    @NotNull
    private String cardBrand;

    private double balanceAmount;

    @NotNull
    private double creditLimit;

    private double totalConsumption;

    private boolean debitor;

    @NotNull
    private String chargeDay;

    @NotNull
    private ClientDTO client;
}
