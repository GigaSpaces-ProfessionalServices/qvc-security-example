package com.gigaspaces.securitytest.common;

import java.io.*;

public class ShippingAddress extends Address implements Serializable {

  private String type; // "residence" / "office"

  public ShippingAddress(String street, String city, String country, String type) {
    super(street, city, country);
    this.type = type;
  }

  public ShippingAddress() {
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
