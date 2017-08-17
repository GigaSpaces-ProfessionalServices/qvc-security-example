package com.gigaspaces.securitytest.common;

import com.gigaspaces.document.DocumentProperties;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LineItem implements Serializable {

  public static final String NAME = "name";
  public static final String QUANTITY = "count";

  private String id;
  private List<Cost> discounts;
  private DocumentProperties data;

  public LineItem(long cartId, DocumentProperties data) {
    this.id = UUID.randomUUID().toString();
    this.data = data;
    discounts = new ArrayList<>();
  }

  public LineItem() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DocumentProperties getData() {
    return data;
  }

  public void setData(DocumentProperties data) {
    this.data = data;
  }

  public List<Cost> getDiscounts() {
    return discounts;
  }

  public void setDiscounts(List<Cost> discounts) {
    this.discounts = discounts;
  }

}
