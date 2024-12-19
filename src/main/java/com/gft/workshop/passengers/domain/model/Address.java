package com.gft.workshop.passengers.domain.model;

import lombok.Data;
import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "adress")
@ValueObject
@Data
public class Address {

  private String street;
  private String city;
  private String postalCode;
}
