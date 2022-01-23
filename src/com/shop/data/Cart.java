package com.shop.data;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private int amount;
    private int totalPrice;
    private Map<Product,Integer> products = new HashMap<>();

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "amount=" + amount +
                ", totalPrice=" + totalPrice +
                ", products=" + products +
                '}';
    }
}
