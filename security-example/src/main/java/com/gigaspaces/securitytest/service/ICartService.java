package com.gigaspaces.securitytest.service;

public interface ICartService {

  String getCart(long cartId);

  boolean createCart(long cartId, String cartJson);

}
