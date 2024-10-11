package com.gft.workshop.passengers.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.data.relational.core.mapping.Table;

@Table("adress")
@ValueObject
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String street;
    private String city;
    private String postalCode;

}
