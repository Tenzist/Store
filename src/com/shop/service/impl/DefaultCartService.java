package com.shop.service.impl;

import com.shop.data.Cart;
import com.shop.data.Customer;
import com.shop.data.Product;
import com.shop.exception.OrderException;
import com.shop.exception.StockException;
import com.shop.service.CartService;
import com.shop.service.CustomerService;
import com.shop.service.ProductService;

import java.util.Map;

public class DefaultCartService implements CartService {

    private CustomerService customerService;
    private ProductService productService;

    public DefaultCartService(CustomerService customerService, ProductService productService) {
        this.customerService = customerService;
        this.productService = productService;
    }

    @Override
    public void addToCart(Product product, int amount) throws StockException {
        int availableStock = productService.getProductStock(product);
        int requiredStock = productService.getProductStock(product) - amount;
        if(requiredStock < 0){
            throw new StockException(String.format("Low stock: available: %d required: %d", availableStock, Math.abs(requiredStock)));
    }
        Map<Product, Integer> products = getProducts();
        if(products.containsKey(product)) {
            int currentAmount = products.get(product);
            products.put(product,amount + currentAmount);
        }
        else {
            products.put(product,amount);
        }
        updateCart();
    }

    @Override
    public void removeFromCart(Product product, int amount) {
        Map<Product, Integer> products = getProducts();
        if (products.containsKey(product) && products.get(product) <= amount) {
            products.remove(product);
        } else if(products.containsKey(product)) {
            int currentAmount = products.get(product);
            products.put(product,currentAmount - amount);
        }
        updateCart();
    }

    @Override
    public Cart getCurrentCart() {
        Customer currentCustomer = customerService.getCurrentCustomer();
        return currentCustomer.getCart();
    }

    private Map<Product,Integer> getProducts() {
        return getCurrentCart().getProducts();
    }

    @Override
    public void updateCart() {
        Cart cart = getCurrentCart();
        Map<Product, Integer> products = cart.getProducts();
        int totalAmount = 0;
        for (Integer amount : products.values()) {
            totalAmount += amount;
        }
        cart.setAmount(totalAmount);
        int totalPrice = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            totalPrice += entry.getKey().getPrice() * entry.getValue();
        }
        cart.setTotalPrice(totalPrice);
    }

    @Override
    public void createNewCart() {
        Customer customer = customerService.getCurrentCustomer();
        customer.setCart(new Cart());
    }

    @Override
    public boolean isCartEmpty() {
        return getCurrentCart().getProducts().isEmpty();
    }
}
