package com.redhat.bankdemo;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Cacheable
public class Customer extends PanacheEntity {

  public Long customerId;
  public String city;
  public String email;
  public String firstName;
  public String lastName;
  public String middleInitial;
  public String phone;
  public String state;
  public String street;
  public String zip;

  public Customer() {

  }

}
