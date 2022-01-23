package com.shop.service.impl;

import com.shop.data.Cart;
import com.shop.data.Customer;
import com.shop.data.Order;
import com.shop.data.Product;
import com.shop.exception.OrderException;
import com.shop.exception.StockException;
import com.shop.service.OrderService;
import com.shop.service.CartService;
import com.shop.service.CustomerService;
import com.shop.service.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DefaultOrderService implements OrderService {

    private CartService cartService;
    private CustomerService customerService;
    private ProductService productService;

    public DefaultOrderService(CartService cartService, CustomerService customerService,
                               ProductService productService) {
        this.cartService = cartService;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Override
    public void makeOrder() throws OrderException {
        if(cartService.isCartEmpty()){
            throw new OrderException("Your cart is empty");
        }
        Cart cart = cartService.getCurrentCart();
        Customer customer = customerService.getCurrentCustomer();
        int totalAmountToPay = cart.getTotalPrice();
        int actualAmount = customer.getMoney();
        if (actualAmount >= totalAmountToPay) {
            removeLowStockProductFromCart(cart);
            updateProductStock(cart);
            customer.setMoney(actualAmount - cart.getTotalPrice());
            customer.getOrders().add(createOrderByCart(cart));
            cartService.createNewCart();
        } else {
            throw new OrderException(String.format("Not enough money. Required: %d , actual %d", totalAmountToPay, actualAmount));
        }
    }

    private Order createOrderByCart(Cart cart) {
        Order order = new Order();
        order.setAmount(cart.getAmount());
        order.setTotalPrice(cart.getTotalPrice());
        order.setProducts(new HashMap<>(cart.getProducts()));
        return order;
    }

    private void removeLowStockProductFromCart(Cart cart) {
        for (Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
            int productStock = productService.getProductStock(entry.getKey());
            int requiredStock = productStock - entry.getValue();
            if (requiredStock < 0) {
                cartService.removeFromCart(entry.getKey(), Math.abs(requiredStock));
            }
        }
    }

    private void updateProductStock(Cart cart) throws OrderException {
        Map<Product, Integer> unchangedProducts = new HashMap<>(productService.getProductStorage());
        try {
            for (Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
                productService.adjustStock(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            productService.getProductStorage().putAll(unchangedProducts);
            throw new OrderException(e.getMessage());
        }
    }
}
