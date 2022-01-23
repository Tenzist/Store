package com.shop.service.impl;

import com.shop.data.Customer;
import com.shop.service.CustomerService;
import com.shop.service.SessionService;

public class DefaultCustomerService implements CustomerService {

    private static final String CUSTOMER_KEY = "customer";

    private final SessionService sessionService;

    public DefaultCustomerService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void addCurrentCustomer(Customer customer) {
        sessionService.addParam(CUSTOMER_KEY, customer);
    }

    @Override
    public Customer getCurrentCustomer() {
        return (Customer) sessionService.getParam(CUSTOMER_KEY);
    }


}
