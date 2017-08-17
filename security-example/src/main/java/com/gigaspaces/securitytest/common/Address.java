package com.gigaspaces.securitytest.common;

import java.io.*;

public class Address implements Serializable {

  public static final String STREET = "street";
  public static final String CITY = "city";
  public static final String COUNTRY = "country";

  private String street;

  private String city;

  private String country;

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Address(String street, String city, String country) {
    this.street = street;
    this.city = city;
    this.country = country;
  }

  public Address() {
    this("", "", "");
  }
}
