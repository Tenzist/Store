package com.shop.service;

import com.shop.data.Customer;

public interface CustomerService {

    void addCurrentCustomer(Customer customer);

    Customer getCurrentCustomer();

}
