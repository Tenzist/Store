package com.shop.service.impl;

import com.shop.ConnentionToDB;
import com.shop.data.Product;
import com.shop.exception.ProductNotFoundException;
import com.shop.exception.StockException;
import com.shop.service.ProductService;

import java.util.*;
import java.util.stream.Collectors;

public class DefaultProductService implements ProductService {

    Map<Product, Integer> products;

    public DefaultProductService() {
        this.products = new HashMap<>();
        products.put(createProduct("Tomato", "V", 10), 30);
        products.put(createProduct("Cucumber", "V", 5), 50);

    }

    private Product createProduct(String name, String category, int price) {
        return new Product(name, category, price);
    }

    @Override
    public List<Product> getAllProduct() {
        return new ArrayList<>(products.keySet());
    }


    @Override
    public List<Product> getProductsByCategory(String category) {
        return products.keySet().stream()
                .filter(product -> product.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductByName(String name) throws ProductNotFoundException {
        return products.keySet().stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product name not found for: " + name));
    }

    @Override
    public void adjustStock(Product product, int amount) throws StockException {
        int availableStock = products.get(product);
        int requiredStock = availableStock - amount;
        if (requiredStock < 0) {
            throw new StockException(String.format("Low stock: available: %d required: %d", availableStock, Math.abs(requiredStock)));
        }
        products.put(product, requiredStock);
    }

    @Override
    public int getProductStock(Product product) {
        return products.get(product);
    }

    @Override
    public Map<Product, Integer> getProductStorage() {
        return products;
    }

}
