package com.shop.service;

import com.shop.data.Cart;
import com.shop.data.Product;
import com.shop.exception.StockException;

public interface CartService {

    void addToCart(Product product, int amount) throws StockException;

    void removeFromCart(Product product, int amount);

    Cart getCurrentCart();

    void updateCart();

    void createNewCart();

    boolean isCartEmpty();
}
