package com.revature.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.*;

import com.revature.models.*;
import com.revature.service.*;

public class Controller {
    CustomerService customerService;
    OrderService orderService;

    public Controller() {
        customerService = new CustomerService();
        orderService = new OrderService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/customer", this::postCustomerHandler);
        app.get("/customer/{customer_id}", this::getCustomerByIDHandler);
        app.patch("/customer/{customer_id}", this::patchCustomerHandler);

        app.post("/order", this::postOrderHandler);
        app.get("/order", this::getAllOrdersHandler);
        app.get("/customer/{customer_id}/order", this::getOrderByCustomerIDHandler);
        app.delete("/order/{order_id}", this::deleteOrderHandler);
        return app;
    }

    private void postCustomerHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Customer customer = mapper.readValue(context.body(), Customer.class);
        Customer addedCustomer = customerService.addNewCustomer(customer);

        if (addedCustomer == null) {
            context.status(400);
        }
        else {
            context.json(mapper.writeValueAsString(addedCustomer));
        }
    }

    private void getCustomerByIDHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        int customer_id = Integer.parseInt(context.pathParam("customer_id"));
        Customer customer = customerService.retrieveCustomerByID(customer_id);

        if (customer != null) {
            context.json(mapper.writeValueAsString(customer));
        }
    }

    private void patchCustomerHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        int customer_id = Integer.parseInt(context.pathParam("customer_id"));
        Customer customer = mapper.readValue(context.body(), Customer.class);
        customer.setCustomer_id(customer_id);

        customer = customerService.updateCustomerByID(customer);

        if (customer == null) {
            context.status(400);
        }
        else {
            context.json(mapper.writeValueAsString(customer));
        }
    }

    private void postOrderHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(context.body(), Order.class);
        Order addedOrder = orderService.addNewOrder(order);

        if (addedOrder == null) {
            context.status(400);
        }
        else {
            context.json(mapper.writeValueAsString(addedOrder));
        }
    }

    private void getAllOrdersHandler(Context context) throws JsonProcessingException {
        context.json(orderService.retrieveAllOrders());
    }

    private void getOrderByCustomerIDHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        int customer_id = Integer.parseInt(context.pathParam("customer_id"));
        List<Order> orders = orderService.retrieveAllOrdersByCustomerID(customer_id);

        if (orders != null) {
            context.json(mapper.writeValueAsString(orders));
        }
    }

    private void deleteOrderHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        int order_id = Integer.parseInt(context.pathParam("order_id"));
        Order order = orderService.deleteOrderByID(order_id);

        if (order != null) {
            context.json(mapper.writeValueAsString(order));
        }
    }

}
