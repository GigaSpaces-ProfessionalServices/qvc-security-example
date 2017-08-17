package com.gigaspaces.securitytest.common;

import com.gigaspaces.document.DocumentProperties;

import java.io.*;
import java.math.BigDecimal;

public class Cost implements Serializable {

  private String name;
  private BigDecimal amount;
  private DocumentProperties properties;

  public Cost(String name, BigDecimal amount) {
    this.name = name;
    this.amount = amount;
    properties = new DocumentProperties();
  }

  public Cost() {
    this("", new BigDecimal(0));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public DocumentProperties getProperties() {
    return properties;
  }

  public void setProperties(DocumentProperties properties) {
    this.properties = properties;
  }

}
