package com.shop.data;

public class Order extends Cart{
    @Override
    public String toString() {
        return "Order{" +
                "amount=" + getAmount() +
                ", totalPrice=" + getTotalPrice() +
                ", products=" + getProducts() +
                '}';
    }
}
