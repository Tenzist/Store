package com.shop.service;

import com.shop.data.Product;
import com.shop.exception.ProductNotFoundException;
import com.shop.exception.StockException;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Map;

public interface ProductService {

    List<Product> getAllProduct();

    List<Product> getProductsByCategory(String category);

    Product getProductByName(String name) throws ProductNotFoundException;

    void adjustStock(Product product, int amount) throws StockException;

    int getProductStock(Product product);

    Map<Product,Integer> getProductStorage();
}
