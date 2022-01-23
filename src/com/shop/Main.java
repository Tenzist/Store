package com.shop;

import com.shop.data.Cart;
import com.shop.data.Customer;
import com.shop.data.Product;
import com.shop.exception.OrderException;
import com.shop.exception.ProductNotFoundException;
import com.shop.exception.StockException;
import com.shop.service.*;
import com.shop.service.impl.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SessionService sessionService = new DefaultSessionService();
        CustomerService customerService = new DefaultCustomerService(sessionService);
        customerService.addCurrentCustomer(new Customer());
        ProductService productService = new DefaultProductService();
        CartService cartService = new DefaultCartService(customerService, productService);
        OrderService orderService = new DefaultOrderService(cartService, customerService,productService);

        Product product;
        int amount = 0;
        String name = "";
        int money = 0;
        while (true) {
            System.out.println("<- Menu ->");
            System.out.println("Your money:" +
                    customerService.getCurrentCustomer().getMoney() +
                    "\n(1) - Show By Category" +
                    " (2) - Add to cart" +
                    " (3) - Show your cart" +
                    " \n(4) - Remove from cart" +
                    " (5) - Show all Products" +
                    " (6) - Buy product"
            );

            Scanner scanner = new Scanner(System.in);
            int choose = scanner.nextInt();
            switch (choose) {
                case 1:
                    scanner = new Scanner(System.in);
                    System.out.println("Name of category");
                    String category = scanner.nextLine();
                    List<Product> products = productService.getProductsByCategory(category);
                    products.forEach(System.out::println);
                    break;

                case 2:
                    scanner = new Scanner(System.in);
                    System.out.println("Name to Add");
                    name = scanner.nextLine();
                    try{
                        product = productService.getProductByName(name);
                        System.out.println("Amount to Add");
                        amount = scanner.nextInt();
                        cartService.addToCart(product, amount);
                    } catch (ProductNotFoundException | StockException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    Cart currentCart = cartService.getCurrentCart();
                    System.out.println(currentCart);
                    break;

                case 4:
                    scanner = new Scanner(System.in);
                    System.out.println("Name to remove");
                    name = scanner.nextLine();
                    try{
                        product = productService.getProductByName(name);
                    } catch (ProductNotFoundException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    System.out.println("Amount to remove");
                    amount = scanner.nextInt();
                    cartService.removeFromCart(product, amount);
                    break;

                case 5:
                    productService.getAllProduct().forEach(System.out::println);
                    break;

                case 6:
                    try {
                        orderService.makeOrder();
                    } catch (OrderException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7:
                    scanner = new Scanner(System.in);
                    System.out.println("Money to add");
                    money = scanner.nextInt();
                    customerService.getCurrentCustomer().setMoney(money);
                    break;
                default:
                    System.out.println("error");
                    break;
                case 8:
                    System.out.println(customerService.getCurrentCustomer().getOrders());
                    break;
            }
        }
    }
}
