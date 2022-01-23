package com.shop.service;

import com.shop.data.Product;
import com.shop.exception.OrderException;

public interface OrderService {

    void makeOrder() throws OrderException;

}
