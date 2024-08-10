package com.revature.models;

public class Customer {

    //Fields should mirror DB columns as closely as possible
    private int customer_id;
    private String name;

    //boilerplate code--------------

    //no-args constructor
    public Customer() {
    }

    //all-args constructor
    public Customer(int customer_id, String name) {
        this.customer_id = customer_id;
        this.name = name;
    }

    //getters and setters
    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //toString so we can print out roles, not memory addresses
    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", name='" + name +
                '}';
    }
}