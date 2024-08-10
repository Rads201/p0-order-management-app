package com.revature.service;

import com.revature.DAOs.*;
import com.revature.models.*;
import java.util.*;

public class CustomerService {
    CustomerDAO customerDAO;

    public CustomerService() {
        customerDAO = new CustomerDAO();
    }

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public Customer addNewCustomer(Customer customer) {
        try {

            if (customer.getName().isEmpty()) {
                return null;
            }
            else {
                return customerDAO.addCustomer(customer);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Customer retrieveCustomerByID(int customer_id) {
        return customerDAO.getCustomerByID(customer_id);
    }

    public Customer updateCustomerByID(Customer customer) {
        try {

            if (customer.getName().isEmpty()) {
                return null;
            }
            else {
                return customerDAO.updateCustomer(customer.getCustomer_id(), customer.getName());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
