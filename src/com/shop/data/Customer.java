package com.shop.data;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private Cart cart = new Cart();
    private List<Order> orders = new ArrayList<>();
    private int money;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
