package com.gigaspaces.securitytest.common;

import java.io.*;

public class BillingAddress extends Address implements Serializable {

  private String billedParty;

  public BillingAddress(String street, String city, String country, String billedParty) {
    super(street, city, country);
    this.billedParty = billedParty;
  }

  public BillingAddress() {
  }

}
